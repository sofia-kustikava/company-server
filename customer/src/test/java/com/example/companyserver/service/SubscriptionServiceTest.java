package com.example.companyserver.service;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.exceptions.SubscriptionPaidException;
import com.example.companyserver.mapper.SubscriptionMapper;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.SubscriptionRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.repo.UserSubscriptionRepo;
import com.example.companyserver.utils.TestingData;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private PayPalService payPalService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private UserEntity user;


    private UserEntity userInactive;
    private UserDto userInactiveDto;

    private SubscriptionEntity subscription;
    private SubscriptionEntity subscription2;

    @BeforeEach
    public void beforeTest() {
        ReflectionTestUtils.setField(subscriptionService, "approvalUrl", "approval_url");
        subscription = TestingData.getSubscription(1L, "Golden");

        UserSubscriptionEntity userSubscription = TestingData.getUserSubscription(LocalDate.now(), SubscriptionStatus.INACTIVE);
        user = TestingData.getUser(1L, null);
        userSubscription.setUser(user);

        UserSubscriptionEntity userSubscription2 = TestingData.getUserSubscription(null, SubscriptionStatus.INACTIVE);
        userSubscription2.setSubscription(subscription);
        userInactive = TestingData.getUser(2L, UserStatus.CREATED);
        userInactiveDto = TestingData.getDtoUser(2L);
        userSubscription2.setUser(userInactive);
        userInactive.setSubscription(userSubscription2);
        subscription2 = TestingData.getSubscription(2L, "Silver");
    }

    @Test
    public void getAllSubscriptionsTest() {
        List<SubscriptionEntity> subscriptions = new ArrayList<>(List.of(subscription, subscription2));
        SubscriptionDto subscriptionDto = TestingData.getSubscriptionDto("Golden");
        SubscriptionDto subscriptionDto2 = TestingData.getSubscriptionDto("Silver");
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>(List.of(subscriptionDto, subscriptionDto2));
        when(subscriptionRepo.findAll()).thenReturn(subscriptions);
        when(subscriptionMapper.subscriptionsToDto(subscriptions)).thenReturn(subscriptionDtos);
        List<SubscriptionDto> actual = subscriptionService.getAllSubscriptions();
        assertEquals(subscriptionDtos, actual);

    }

    @Test
    public void chooseSubscriptionTest() {
        when(authenticationService.getUser()).thenReturn(user);
        when(subscriptionRepo.findById(subscription.getId())).thenReturn(Optional.of(subscription));
        subscriptionService.chooseSubscription(subscription.getId());
        verify(userSubscriptionRepo).save(user.getSubscription());
        verify(userRepo).save(user);
    }

    @Test
    public void paySubscriptionTest() {
        when(userRepo.findById(userInactive.getId())).thenReturn(Optional.of(userInactive));
        when(userMapper.userToDto(userInactive)).thenReturn(userInactiveDto);
        subscriptionService.paySubscription(userInactive.getId());
        verify(userSubscriptionRepo).save(userInactive.getSubscription());
        verify(userRepo).save(userInactive);
        verify(mailService).sendEmailBeginSubscription(userInactiveDto, userInactive.getSubscription().getSubscription());
    }

    @Test
    public void changeSubscriptionTest() {
        when(authenticationService.getUser()).thenReturn(userInactive);
        when(subscriptionRepo.findById(userInactive.getId())).thenReturn(Optional.of(subscription2));
        when(userMapper.userToDto(userInactive)).thenReturn(userInactiveDto);
        subscriptionService.changeSubscription(userInactive.getId());
        verify(userRepo).save(userInactive);
    }

//    @Test
//    public void userAlreadyPaidException() {
//        UserSubscriptionEntity userPaidSubscription = TestingData.getUserSubscription(LocalDate.now().minusDays(3), SubscriptionStatus.ACTIVE);
//        userPaidSubscription.setSubscription(subscription);
//        UserEntity userPaid = TestingData.getUser(3L, UserStatus.ACTIVE);
//        userPaidSubscription.setUser(userPaid);
//        userPaid.setSubscription(userPaidSubscription);
//        when(authenticationService.getUser()).thenReturn(userPaid);
//        assertThrows(SubscriptionPaidException.class, () -> subscriptionService.paymentForSubscription(request));
//    }

    @Test
    public void userHaveSubscriptionTest() {
        when(authenticationService.getUser()).thenReturn(userInactive);
        assertThrows(HaveSubscriptionException.class, () -> subscriptionService.chooseSubscription(subscription.getId()));
    }
}
