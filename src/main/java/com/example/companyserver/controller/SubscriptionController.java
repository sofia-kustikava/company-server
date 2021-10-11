package com.example.companyserver.controller;

import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    public final SubscriptionService subscriptionService;

    @PostMapping("/sub/{id}")
    public HttpStatus chooseSubscription(@PathVariable("id") Long id, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.chooseSubscription(id, userSubscriptionDto);
        return HttpStatus.OK;
    }

    @PostMapping("pay/{id}")
    public HttpStatus paySubscription(@PathVariable("id") Long id) {
        subscriptionService.paySubscription(id);
        return HttpStatus.OK;
    }
}
