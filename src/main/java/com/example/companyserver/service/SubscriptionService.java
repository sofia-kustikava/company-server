package com.example.companyserver.service;

import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.*;
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

    public void chooseSubscription(Long id, UserSubscriptionDto userSubscriptionDto) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        if (user.getSubscription() == null) {
            String findName = userSubscriptionDto.getSubscription();
            SubscriptionEntity subscriptionName = subscriptionRepo.findByName(findName)
                    .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", findName)));

            UserSubscriptionEntity subscription = UserSubscriptionEntity.builder()
                    .subscription(subscriptionName)
                    .user(user)
                    .dateStart(null)
                    .dateEnd(null)
                    .subscriptionStatus(SubscriptionStatus.INACTIVE)
                    .build();
            userSubscriptionRepo.save(subscription);
            user.setSubscription(subscription);
            userRepo.save(user);
        }
    }

    public void paySubscription(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        LocalDate date = LocalDate.from(LocalDate.now().plusDays(30));

        //payment is missing

        user.getSubscription().setDateStart(LocalDate.now());
        user.getSubscription().setDateEnd(date);
        user.getSubscription().setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        user.setStatus(UserStatus.ACTIVE);

        userSubscriptionRepo.save(user.getSubscription());
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), user.getSubscription().getSubscription());
    }

    public void changeSubscription(Long id) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        String findName = user.getSubscription().getSubscription().getName();
        SubscriptionEntity subscriptionName = subscriptionRepo.findByName(findName)
                .orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", findName)));

        if (user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE) || user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.PAUSED)) {
            user.getSubscription().setSubscriptionStatus(SubscriptionStatus.INACTIVE);
            user.getSubscription().setDateEnd(LocalDate.now());
        }

        UserSubscriptionEntity subscription = UserSubscriptionEntity.builder()
                .subscription(subscriptionName)
                .user(user)
                .dateStart(null)
                .dateEnd(null)
                .subscriptionStatus(SubscriptionStatus.INACTIVE)
                .build();
        userSubscriptionRepo.save(subscription);
        user.setSubscription(subscription);
        userRepo.save(user);
    }

    public void continueSubscription() {

    }

    public void stopSubscription() {}
}
