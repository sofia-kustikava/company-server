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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        String findName = userSubscriptionDto.getSubscription();
        SubscriptionEntity subscriptionName = subscriptionRepo.findByName(findName).orElseThrow(() -> new SubscriptionNotExistException(String.format("%s", findName)));

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

    public void paySubscription(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        UserSubscriptionEntity userSubscription = userSubscriptionRepo.findByUser(user).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        Date date = Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());

        //payment is missing

        userSubscription.setDateStart(LocalDateTime.now());
        userSubscription.setDateEnd(date);
        userSubscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        user.setStatus(UserStatus.ACTIVE);

        userSubscriptionRepo.save(userSubscription);
        userRepo.save(user);
        mailService.sendEmailBeginSubscription(userMapper.userToDto(user), userSubscription.getSubscription());
    }
}
