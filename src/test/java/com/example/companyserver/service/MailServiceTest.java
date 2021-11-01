package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.SubscriptionEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserSubscriptionEntity;
import com.example.companyserver.utils.SubscriptionData;
import com.example.companyserver.utils.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailService mailService;

    private UserDto userDto;
    private SubscriptionEntity subscription;
    private UserEntity user;
    private RegisterDto registerUser;
    private SimpleMailMessage mail;
    private UserSubscriptionEntity userSubscription;

    @BeforeEach
    public void beforeTest() {
        subscription = SubscriptionData.getSubscription();

        userSubscription = SubscriptionData.getUserSubscription(LocalDate.now());
        userSubscription.setSubscription(subscription);

        userDto = SubscriptionData.getUserDto(1L);
        user = SubscriptionData.getUser(1L);
        user.setSubscription(userSubscription);
        userSubscription.setUser(user);

        registerUser = UserData.getRegisterUser();
        mail = new SimpleMailMessage();
    }

    @Test
    public void sendEmailRegistrationTest() {
        mail.setTo(registerUser.getEmail());
        mail.setSubject("Registration completed! Enjoy ;)");
        mail.setText("Your account is working!");
        mailService.sendEmailRegistration(registerUser);
        Mockito.verify(javaMailSender).send(mail);
    }

    @Test
    public void sendEmailBeginSubscription() {
        mail.setTo(userDto.getEmail());
        mail.setSubject("You've just started to follow by " + subscription.getName() + " completed! Enjoy ;)\n" +
                "Description: " + subscription.getDescription());
        mail.setText("Your account is working!");
        mailService.sendEmailBeginSubscription(userDto, subscription);
        Mockito.verify(javaMailSender).send(mail);
    }

    @Test
    public void sendEmailSubscriptionWillExpire() {
        mail.setTo(user.getEmail());
        mail.setSubject("Expiration date: " + user.getSubscription().getDateEnd());
        mail.setText("Your " + user.getSubscription().getSubscription().getName() +
                " subscription will be expired at " + user.getSubscription().getDateEnd() +
                "! Hurry up and update your subscription ;)\n" +
                user.getSubscription().getSubscription().getName() +
                " description: " + user.getSubscription().getSubscription().getDescription());
        mailService.sendEmailSubscriptionWillExpire(user);
        Mockito.verify(javaMailSender).send(mail);
    }

    @Test
    public void sendEmailSubscriptionExpired() {
        mail.setTo(user.getEmail());
        mail.setSubject("Expiration date: " + user.getSubscription().getDateEnd());
        mail.setText("Your " + user.getSubscription().getSubscription().getName() +
                " subscription is expired. You can update your subscription or buy a new one;)\n" +
                user.getSubscription().getSubscription().getName() +
                " description: " + user.getSubscription().getSubscription().getDescription());
        mailService.sendEmailSubscriptionExpired(user);
        Mockito.verify(javaMailSender).send(mail);
    }
}
