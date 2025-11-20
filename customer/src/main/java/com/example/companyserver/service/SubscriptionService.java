package com.example.companyserver.service;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.exceptions.SubscriptionNotExistException;
import com.example.companyserver.exceptions.SubscriptionPaidException;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.mapper.SubscriptionMapper;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.repo.UserSubscriptionRepo;
import com.example.companyserver.service.AuthenticationService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    @Value("${paypal.approval.url}")
    private String approvalUrl;

    private final UserRepo userRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final PayPalService payPalService;
    private final AuthenticationService authenticationService;
    private final SubscriptionMapper subscriptionMapper;

    public List<SubscriptionDto> getAllSubscriptions() {
        List<SubscriptionEntity> subscriptions = subscriptionRepo.findAll();
        return subscriptionMapper.subscriptionsToDto(subscriptions);
    }

    public void chooseSubscription(Long id) {
        UserEntity user = authenticationService.getUser();
        if (user.getSubscription() == null) {
            UserSubscriptionEntity subscription = UserSubscriptionEntity.builder()
                    .subscription(subscriptionRepo.findById(id)
                            .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", id))))
                    .user(user)
                    .dateStart(null)
                    .dateEnd(null)
                    .subscriptionStatus(SubscriptionStatus.INACTIVE)
                    .build();
            userSubscriptionRepo.save(subscription);
            user.setSubscription(subscription);
            userRepo.save(user);

        } else {
            log.info("You already have a subscription: {}", user.getSubscription().getSubscription().getName());
            throw new HaveSubscriptionException(String.format("%s", user.getSubscription().getSubscription().getName()));
        }
    }

    public String paymentForSubscription(HttpServletRequest request) throws PayPalRESTException {
        Long userId = authenticationService.getAuthUserId(request);
        UserSubscriptionEntity user = userSubscriptionRepo.findByUserId(userId);
        if (user != null && user.getSubscriptionStatus().equals(SubscriptionStatus.INACTIVE)) {
            Payment payment = payPalService.createPayment(
                    userId,
                    user.getSubscription().getPrice(),
                    user.getSubscription().getDescription());
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals(approvalUrl)) return link.getHref();
            }
        } else {
            log.info("User don't have an access to pay for the subscription : {}", userId);
            throw new SubscriptionPaidException(String.format("%s", userId));
        }
        return null;
    }

    public void paySubscription(Long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        UserSubscriptionEntity userChange = user.getSubscription();
        userChange.setDateStart(LocalDate.now());
        userChange.setDateEnd(LocalDate.from(LocalDate.now().plusDays(30)));
        userChange.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        user.setStatus(UserStatus.ACTIVE);

        userSubscriptionRepo.save(userChange);
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), userChange.getSubscription());
    }

    public void changeSubscription(Long id) {
        UserEntity user = authenticationService.getUser();
        if (user.getSubscription().getSubscription().getId().equals(id)) throw new HaveSubscriptionException(String.format("%s", id));
        user.getSubscription().setSubscription(subscriptionRepo.findById(id)
                .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", id))));
        user.getSubscription().setDateStart(null);
        user.getSubscription().setDateEnd(null);
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.INACTIVE)) {
            user.getSubscription().setSubscriptionStatus(SubscriptionStatus.INACTIVE);
        }
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
    }
}
