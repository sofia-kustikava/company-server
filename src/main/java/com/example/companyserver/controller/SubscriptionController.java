package com.example.companyserver.controller;

import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    public final SubscriptionService subscriptionService;

    @PostMapping("{userId}/create")
    public ResponseEntity<String> chooseSubscription(@PathVariable("userId") Long userId, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.chooseSubscription(userId, userSubscriptionDto);
        return new ResponseEntity<>("You chose subscription " + userSubscriptionDto.getSubscription(), HttpStatus.OK);
    }

    @PostMapping("{userId}/pay")
    public ResponseEntity<String> paySubscription(@PathVariable("userId") Long userId) {
        subscriptionService.paySubscription(userId);
        return new ResponseEntity<>("Your subscription is active", HttpStatus.OK);
    }

    @PostMapping("{userId}/change")
    public ResponseEntity<String> changeSubscription(@PathVariable("userId") Long userId, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.changeSubscription(userId, userSubscriptionDto);
        return new ResponseEntity<>("You changed your subscription to " + userSubscriptionDto.getSubscription() , HttpStatus.OK);
    }
}
