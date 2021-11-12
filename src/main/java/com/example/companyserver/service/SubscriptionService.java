package com.example.companyserver.service;

import com.example.companyserver.dto.SubscriptionNameDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.*;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.repo.UserSubscriptionRepo;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserRepo userRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final PayPalService payPalService;
    private final AuthenticationService authenticationService;

    public void chooseSubscription(SubscriptionNameDto name) {
        UserEntity user = authenticationService.getUser();
        if (user.getSubscription() == null) {
            UserSubscriptionEntity subscription = UserSubscriptionEntity.builder()
                    .subscription(subscriptionRepo.findByName(name.getName())
                            .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", name.getName()))))
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

    public String paymentForSubscription() throws PayPalRESTException {
        UserEntity user = authenticationService.getUser();
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) {
            Payment payment = payPalService.createPayment(
                    user.getId(),
                    user.getSubscription().getSubscription().getPrice(),
                    user.getSubscription().getSubscription().getDescription());
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) return link.getHref();
            }
        } else {
            log.info("You already paid for the subscription: {}", user.getSubscription().getSubscription().getName());
            throw new SubscriptionPaidException(String.format("%s", user.getSubscription().getSubscription().getName()));
        }
        return null;
    }

    public void paySubscription(Long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        user.getSubscription().setDateStart(LocalDate.now());
        user.getSubscription().setDateEnd(LocalDate.from(LocalDate.now().plusDays(30)));
        user.getSubscription().setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        user.setStatus(UserStatus.ACTIVE);

        userSubscriptionRepo.save(user.getSubscription());
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
    }

    public void changeSubscription(SubscriptionNameDto name) {
        UserEntity user = authenticationService.getUser();
        if (user.getSubscription().getSubscription().getName().equals(name.getName())) throw new HaveSubscriptionException(name.getName());
        user.getSubscription().setSubscription(subscriptionRepo.findByName(name.getName())
                .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", name.getName()))));
        user.getSubscription().setDateStart(null);
        user.getSubscription().setDateEnd(null);
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.INACTIVE)) {
            user.getSubscription().setSubscriptionStatus(SubscriptionStatus.INACTIVE);
        }
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
    }
}
