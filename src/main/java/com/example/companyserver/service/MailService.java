package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendEmailRegistration(RegisterDto user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Registration completed! Enjoy ;)");
        mail.setText("Your account is working!");

        javaMailSender.send(mail);
    }

    public void sendEmailBeginSubscription(UserDto user, SubscriptionEntity subscription) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("You've just started to follow by " + subscription.getName() + " completed! Enjoy ;)\n" +
                "Description: " + subscription.getDescription());
        mail.setText("Your account is working!");

        javaMailSender.send(mail);
    }
}
