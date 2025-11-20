package com.example.companyserver.service;

import com.example.companyserver.entity.SubscriptionStatus;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final MailService mailService;
    private final UserRepo userRepo;

    @Scheduled(cron = "0 0 0 * * ?")
    public void isSubscriptionExpired() {
        userRepo.findAllByEndDate(LocalDate.now()).stream()
                .filter(user -> user.getSubscription().getDateEnd().equals(LocalDate.now()))
                .forEach(user -> {
                    if (user.getStatus().equals(UserStatus.ACTIVE)) {
                        user.getSubscription().setSubscriptionStatus(SubscriptionStatus.PAUSED);
                        user.setStatus(UserStatus.BANNED);
                    }
                    userRepo.save(user);
                    mailService.sendEmailSubscriptionExpired(user);
                });
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void isSubscriptionWillExpiredIn3Days() {
        userRepo.findAllByEndDate(LocalDate.now().plusDays(3)).stream()
                .filter(user -> user.getSubscription().getDateEnd().plusDays(3).equals(LocalDate.now()))
                .forEach(mailService::sendEmailSubscriptionWillExpire);
    }
}
