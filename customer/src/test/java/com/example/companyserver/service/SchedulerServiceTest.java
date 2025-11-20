package com.example.companyserver.service;

import com.example.companyserver.entity.*;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.utils.TestingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private MailService mailService;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private SchedulerService schedulerService;

    private UserEntity user;
    private UserSubscriptionEntity userSubscription;

    private UserEntity user2;
    private UserSubscriptionEntity userSubscription2;

    private List<UserEntity> users = new ArrayList<>();
    private SubscriptionEntity subscription;

    @BeforeEach
    public void beforeTest() {
        subscription = TestingData.getSubscription(1L, "Golden");

        userSubscription = TestingData.getUserSubscription(LocalDate.now(), SubscriptionStatus.ACTIVE);
        userSubscription.setSubscription(subscription);
        user = TestingData.getUser(1L, UserStatus.ACTIVE);
        user.setSubscription(userSubscription);

        userSubscription2 = TestingData.getUserSubscription(LocalDate.now().minusDays(3), SubscriptionStatus.ACTIVE);
        userSubscription2.setSubscription(subscription);
        user2 = TestingData.getUser(2L, UserStatus.ACTIVE);
        user2.setSubscription(userSubscription2);

        userSubscription2.setUser(user2);
        userSubscription.setUser(user);

        users.addAll(Arrays.asList(user, user2));
    }

    @Test
    public void isSubscriptionExpiredTest() {
        when(userRepo.findAllByEndDate(LocalDate.now())).thenReturn(users);
        schedulerService.isSubscriptionExpired();
        verify(userRepo).save(user);
        verify(mailService).sendEmailSubscriptionExpired(user);

    }

    @Test
    public void isSubscriptionWillExpiredIn3DaysTest() {
        when(userRepo.findAllByEndDate(LocalDate.now().plusDays(3))).thenReturn(users);
        schedulerService.isSubscriptionWillExpiredIn3Days();
        verify(mailService).sendEmailSubscriptionWillExpire(user2);
    }
}