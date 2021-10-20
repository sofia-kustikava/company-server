package com.example.companyserver.service;

import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.exceptions.SubscriptionNotExistException;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.repo.UserSubscriptionRepo;
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

    public void chooseSubscription(Long userId, UserSubscriptionDto userSubscriptionDto) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));
        if (user.getSubscription() == null) {
            UserSubscriptionEntity subscription = UserSubscriptionEntity.builder()
                    .subscription(subscriptionRepo.findByName(userSubscriptionDto.getSubscription())
                            .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", userSubscriptionDto.getSubscription()))))
                    .user(user)
                    .dateStart(null)
                    .dateEnd(null)
                    .subscriptionStatus(SubscriptionStatus.INACTIVE)
                    .build();
            userSubscriptionRepo.save(subscription);
            user.setSubscription(subscription);
            userRepo.save(user);
        } else {
            log.info("You already have a subscription: " + user.getSubscription().getSubscription().getName());
            throw new HaveSubscriptionException(String.format("%s", user.getSubscription().getSubscription().getName()));
        }
    }

    public void paySubscription(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));

        //payment is missing

        user.getSubscription().setDateStart(LocalDate.now());
        user.getSubscription().setDateEnd(LocalDate.from(LocalDate.now().plusDays(30)));
        user.getSubscription().setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        user.setStatus(UserStatus.ACTIVE);

        userSubscriptionRepo.save(user.getSubscription());
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
    }

    public void changeSubscription(Long userId, UserSubscriptionDto userSubscriptionDto) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));

        user.getSubscription().setSubscription(subscriptionRepo.findByName(userSubscriptionDto.getSubscription())
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
