package com.example.companyserver.controller;

import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    public final SubscriptionService subscriptionService;

    @PostMapping("{userId}/create")
    public HttpStatus chooseSubscription(@PathVariable("userId") Long userId, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.chooseSubscription(userId, userSubscriptionDto);
        return HttpStatus.OK;
    }

    @PostMapping("{userId}/pay")
    public HttpStatus paySubscription(@PathVariable("userId") Long userId) {
        subscriptionService.paySubscription(userId);
        return HttpStatus.OK;
    }

    @PostMapping("{userId}/change")
    public HttpStatus changeSubscription(@PathVariable("userId") Long userId, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.changeSubscription(userId, userSubscriptionDto);
        return HttpStatus.OK;
    }
}
