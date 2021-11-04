package com.example.companyserver.controller;

import com.example.companyserver.dto.SubscriptionNameDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.exceptions.PayPalException;
import com.example.companyserver.service.PayPalService;
import com.example.companyserver.service.SubscriptionService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    public final SubscriptionService subscriptionService;
    public final PayPalService payPalService;

    @PostMapping("{userId}/create")
    public ResponseEntity<String> chooseSubscription(@PathVariable("userId") Long userId, @RequestBody SubscriptionNameDto name) throws PayPalRESTException {
        subscriptionService.chooseSubscription(userId, name);
        return new ResponseEntity<>("You chose subscription " + name.getName(), HttpStatus.OK);
    }

    @PostMapping("{userId}/payment")
    public String paymentForSubscription(@PathVariable("userId") Long userId) throws PayPalRESTException {
        return subscriptionService.paymentForSubscription(userId);
    }

    @PostMapping("{userId}/change")
    public ResponseEntity<String> changeSubscription(@PathVariable("userId") Long userId, @RequestBody UserSubscriptionDto userSubscriptionDto) {
        subscriptionService.changeSubscription(userId, userSubscriptionDto);
        return new ResponseEntity<>("You changed your subscription to " + userSubscriptionDto.getSubscription() , HttpStatus.OK);
    }

    @GetMapping("{userId}/success")
    public ResponseEntity<String> successPayment(@PathVariable("userId") Long userId, HttpServletRequest request) throws PayPalRESTException {
        payPalService.executePayment(request.getParameter("paymentId"), request.getParameter("PayerID"));
        subscriptionService.paySubscription(userId);
        return new ResponseEntity<>("Payment was successful" , HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment(){
        return new ResponseEntity<>("Payment was canceled" , HttpStatus.OK);
    }
}
