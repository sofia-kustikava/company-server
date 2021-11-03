package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.repo.UserSubscriptionRepo;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserSubscriptionRepo userSubscriptionRepo;

    @Mock
    private SubscriptionRepo subscriptionRepo;

    @Mock
    private MailService mailService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private List<CompanyEntity> companiesEntity = new ArrayList<>();
    private List<CompanyDto> companiesDto = new ArrayList<>();
    private List<CompanyEntity> companies = new ArrayList<>();

    private CompanyEntity companyEntity;
    private CompanyEntity companyEntity2;

    private UserEntity user;
    private UserSubscriptionEntity userSubscription;
    private UserSubscriptionDto userSubscriptionDto;

    private UserEntity user2;
    private UserSubscriptionEntity userSubscription2;
    private UserSubscriptionDto userSubscriptionDto2;

    private List<UserEntity> users = new ArrayList<>();
    private SubscriptionEntity subscription;

    @BeforeEach
    public void beforeTest() {
        companiesEntity.add(TestingData.getCompany("ONFA1"));
        companiesDto.add(TestingData.getCompanyDto("ONFA1"));

        companyEntity = TestingData.getCompany("ONFA1");
        companyEntity2 = TestingData.getCompany("ONFA2");
        companies.addAll(Arrays.asList(companyEntity, companyEntity2));

        subscription = TestingData.getSubscription();

        userSubscription = TestingData.getUserSubscription(LocalDate.now());
        userSubscriptionDto = TestingData.getUserSubscriptionDto(LocalDate.now());
        user = TestingData.getUser(1L, null);

        userSubscription2 = TestingData.getUserSubscription(LocalDate.now().minusDays(3));
        userSubscriptionDto2 = TestingData.getUserSubscriptionDto(LocalDate.now().minusDays(3));
        userSubscription2.setSubscription(subscription);
        user2 = TestingData.getUser(2L, null);

        userSubscription2.setUser(user2);
        user2.setSubscription(userSubscription2);
        userSubscription.setUser(user);

        users.addAll(Arrays.asList(user, user2));
    }

    @Test
    public void userHaveSubscriptionTest() {
//        when(userRepo.findById(user2.getId())).thenReturn(Optional.of(user2));
//        assertThrows(HaveSubscriptionException.class, () -> subscriptionService.chooseSubscription(user2.getId(), userSubscriptionDto2));
    }
}
