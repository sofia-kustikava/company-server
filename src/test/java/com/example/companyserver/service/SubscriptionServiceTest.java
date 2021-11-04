package com.example.companyserver.service;

import com.example.companyserver.dto.SubscriptionNameDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.exceptions.SubscriptionPaidException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    @InjectMocks
    private SubscriptionService subscriptionService;

    private UserEntity user;

    private SubscriptionNameDto subscriptionNameDto;
    private SubscriptionNameDto subscriptionNameDtoInactive;

    private UserEntity userInactive;
    private UserDto userInactiveDto;

    private UserEntity userPaid;
    private SubscriptionEntity subscription;

    @BeforeEach
    public void beforeTest() {
        subscription = TestingData.getSubscription();

        UserSubscriptionEntity userSubscription = TestingData.getUserSubscription(LocalDate.now(), SubscriptionStatus.INACTIVE);
        subscriptionNameDto = TestingData.getSubscriptionName("Golden");
        user = TestingData.getUser(1L, null);
        userSubscription.setUser(user);

        UserSubscriptionEntity userSubscription2 = TestingData.getUserSubscription(null, SubscriptionStatus.INACTIVE);
        subscriptionNameDtoInactive = TestingData.getSubscriptionName("Golden");
        userSubscription2.setSubscription(subscription);
        userInactive = TestingData.getUser(2L, UserStatus.CREATED);
        userInactiveDto = TestingData.getDtoUser(2L);
        userSubscription2.setUser(userInactive);
        userInactive.setSubscription(userSubscription2);
    }

    @Test
    public void chooseSubscriptionTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(subscriptionRepo.findByName(subscriptionNameDto.getName())).thenReturn(Optional.of(subscription));
        subscriptionService.chooseSubscription(user.getId(), subscriptionNameDto);
        verify(userSubscriptionRepo).save(user.getSubscription());
        verify(userRepo).save(user);
    }

    @Test
    public void paymentForSubscriptionTest() throws PayPalRESTException {
        Payment pay = new Payment();

        String link ="https://www.sandbox.paypal.com";
        when(userRepo.findById(userInactive.getId())).thenReturn(Optional.of(userInactive));
        when(payPalService.createPayment(
                userInactive.getId(), subscription.getPrice(), subscription.getDescription())).thenReturn(pay.setLinks(new ArrayList<>(Arrays.asList(new Links(link, "approval_url")))));
        String payment = subscriptionService.paymentForSubscription(userInactive.getId());
        assertEquals(link, payment);
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
        when(userRepo.findById(userInactive.getId())).thenReturn(Optional.of(userInactive));
        when(subscriptionRepo.findByName(subscriptionNameDto.getName())).thenReturn(Optional.of(subscription));
        when(userMapper.userToDto(userInactive)).thenReturn(userInactiveDto);
        subscriptionService.changeSubscription(userInactive.getId(), subscriptionNameDtoInactive);
        verify(userRepo).save(userInactive);
    }

    @Test
    public void userAlreadyPaidException() {
        UserSubscriptionEntity userPaidSubscription = TestingData.getUserSubscription(LocalDate.now().minusDays(3), SubscriptionStatus.ACTIVE);
        userPaidSubscription.setSubscription(subscription);
        userPaid = TestingData.getUser(3L, UserStatus.ACTIVE);

        userPaidSubscription.setUser(userPaid);
        userPaid.setSubscription(userPaidSubscription);
        when(userRepo.findById(userPaid.getId())).thenReturn(Optional.of(userPaid));
        assertThrows(SubscriptionPaidException.class, () -> subscriptionService.paymentForSubscription(userPaid.getId()));
    }

    @Test
    public void userHaveSubscriptionTest() {
        when(userRepo.findById(userInactive.getId())).thenReturn(Optional.of(userInactive));
        assertThrows(HaveSubscriptionException.class, () -> subscriptionService.chooseSubscription(userInactive.getId(), subscriptionNameDtoInactive));
    }
}
