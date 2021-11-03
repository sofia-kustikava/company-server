package com.example.companyserver.service;

import com.example.companyserver.dto.SubscriptionNameDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.exceptions.PayPalException;
import com.example.companyserver.exceptions.SubscriptionNotExistException;
import com.example.companyserver.exceptions.UserNotFoundException;
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

    public void chooseSubscription(Long userId, SubscriptionNameDto name) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));
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

    public Links paymentForSubscription(Long userId) throws PayPalRESTException {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));
        Payment payment = payPalService.createPayment(
                user.getSubscription().getSubscription().getPrice(),
                user.getSubscription().getSubscription().getDescription());
        return payment.getLinks().get(1);
    }

    public void paySubscription(Long id) throws PayPalException {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        try {
            payPalService.executePayment(user.getSubscription().getId() , user.getId());

            user.getSubscription().setDateStart(LocalDate.now());
            user.getSubscription().setDateEnd(LocalDate.from(LocalDate.now().plusDays(30)));
            user.getSubscription().setSubscriptionStatus(SubscriptionStatus.ACTIVE);
            user.setStatus(UserStatus.ACTIVE);

            userSubscriptionRepo.save(user.getSubscription());
            userRepo.save(user);
            mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
        } catch (PayPalRESTException e) {
            throw new PayPalException(e.getMessage());
        }
    }

    public void changeSubscription(Long userId, UserSubscriptionDto userSubscriptionDto) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));

        user.getSubscription().setSubscription(subscriptionRepo.findByName(userSubscriptionDto.getSubscription().getName())
                .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", userSubscriptionDto.getSubscription()))));
        user.getSubscription().setDateStart(LocalDate.now());
        user.getSubscription().setDateEnd(LocalDate.from(LocalDate.now().plusDays(30)));

        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.INACTIVE)) {
            user.getSubscription().setSubscriptionStatus(SubscriptionStatus.INACTIVE);
        }
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
    }
}
