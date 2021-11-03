package com.example.companyserver.controller;

import com.example.companyserver.dto.SubscriptionNameDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.exceptions.PayPalException;
import com.example.companyserver.service.SubscriptionService;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
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
    public ResponseEntity<String> chooseSubscription(@PathVariable("userId") Long userId, @RequestBody SubscriptionNameDto name) throws PayPalRESTException {
        subscriptionService.chooseSubscription(userId, name);
        return new ResponseEntity<>("You chose subscription " + name.getName(), HttpStatus.OK);
    }

    @PostMapping("{userId}/payment")
    public Links paymentForSubscription(@PathVariable("userId") Long userId) throws PayPalRESTException {
        return subscriptionService.paymentForSubscription(userId);
    }

    @PostMapping("{userId}/pay")
    public ResponseEntity<String> paySubscription(@PathVariable("userId") Long userId) throws PayPalException {
        subscriptionService.paySubscription(userId);
        return new ResponseEntity<>("Your subscription is active", HttpStatus.OK);
    }

    @PostMapping("{userId}/change")
    public ResponseEntity<String> changeSubscription(@PathVariable("userId") Long userId, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.changeSubscription(userId, userSubscriptionDto);
        return new ResponseEntity<>("You changed your subscription to " + userSubscriptionDto.getSubscription() , HttpStatus.OK);
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPayment(){
        return new ResponseEntity<>("Payment was successful" , HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment(){
        return new ResponseEntity<>("Payment was canceled" , HttpStatus.OK);
    }
}
