package com.example.companyserver.controller;

import com.example.companyserver.dto.SubscriptionNameDto;
import com.example.companyserver.service.PayPalService;
import com.example.companyserver.service.SubscriptionService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    public final SubscriptionService subscriptionService;
    public final PayPalService payPalService;

    @PostMapping("/create")
    public ResponseEntity<String> chooseSubscription(@RequestBody SubscriptionNameDto name) {
        subscriptionService.chooseSubscription(name);
        return new ResponseEntity<>("You chose subscription " + name.getName(), HttpStatus.OK);
    }

    @PostMapping("/payment")
    public String paymentForSubscription() throws PayPalRESTException {
        return subscriptionService.paymentForSubscription();
    }

    @PostMapping("/change")
    public ResponseEntity<String> changeSubscription(@RequestBody SubscriptionNameDto name) {
        subscriptionService.changeSubscription(name);
        return new ResponseEntity<>("You changed your subscription to " + name.getName() , HttpStatus.OK);
    }

    @GetMapping("success/{userId}")
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