package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.repo.UserSubscriptionRepo;
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
import static org.mockito.Mockito.verify;
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
        companiesEntity.add(CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build()
        );

        companiesDto.add(CompanyDto.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build()
        );
        companyEntity = CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build();
        companyEntity2 = CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONF2A")
                .type("Common Stock")
                .build();

        companies.addAll(Arrays.asList(companyEntity, companyEntity2));

        subscription = SubscriptionEntity.builder()
                .name("Golden")
                .description("Description sample")
                .price(90D)
                .build();

        userSubscription = UserSubscriptionEntity.builder()
                .subscription(subscription)
                .user(user)
                .dateEnd(LocalDate.now().minusDays(3))
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
        userSubscriptionDto = UserSubscriptionDto.builder()
                .subscription(subscription)
                .user(1L)
                .dateEnd(LocalDate.now().minusDays(3))
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
        user = UserEntity.builder()
                .id(1L)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .status(UserStatus.ACTIVE)
                .subscription(null)
                .build();

        userSubscription2 = UserSubscriptionEntity.builder()
                .subscription(subscription)
                .user(user2)
                .dateEnd(LocalDate.now().minusDays(3))
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
        userSubscriptionDto2 = UserSubscriptionDto.builder()
                .subscription(subscription)
                .dateEnd(LocalDate.now().minusDays(3))
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
        user2 = UserEntity.builder()
                .id(2L)
                .firstName("User2")
                .lastName("Userovich2")
                .email("user2@mail.com")
                .status(UserStatus.ACTIVE)
                .subscription(userSubscription2)
                .build();

        userSubscription2.setUser(user2);
        userSubscription.setUser(user);

        users.addAll(Arrays.asList(user, user2));
    }

    @Test
    public void chooseSubscriptionTest() {
//        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
//        when(subscriptionRepo.findByName("Golden")).thenReturn(Optional.of(subscription));
//        subscriptionService.chooseSubscription(user.getId(), userSubscriptionDto);
//        verify(userSubscriptionRepo).save(userSubscription);
//        verify(userRepo).save(user);
    }

    @Test
    public void userHaveSubscriptionTest() {
        when(userRepo.findById(user2.getId())).thenReturn(Optional.of(user2));
        assertThrows(HaveSubscriptionException.class, () -> subscriptionService.chooseSubscription(user2.getId(), userSubscriptionDto2));
    }
}
