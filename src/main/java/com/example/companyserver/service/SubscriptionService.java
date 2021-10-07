package com.example.companyserver.service;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.entity.UserSubscriptionEntity;
import com.example.companyserver.entity.SubscriptionStatus;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
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
    private final SubscriptionRepo subscriptionRepo;

    public void chooseSubscription(SubscriptionDto subscriptionDto, Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));

        Date date = Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserSubscriptionEntity subscription = UserSubscriptionEntity.builder()
                .dateStart(LocalDateTime.now())
                .dateEnd(date)
                .subscriptionStatus(SubscriptionStatus.INACTIVE)
                .build();

        subscriptionRepo.save(subscription);
        user.setSubscription(subscription);
    }
}
