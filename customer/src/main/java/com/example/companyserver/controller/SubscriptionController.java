package com.example.companyserver.controller;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.service.PayPalService;
import com.example.companyserver.service.SubscriptionService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    public final SubscriptionService subscriptionService;
    public final PayPalService payPalService;

    @GetMapping("/all")
    public List<SubscriptionDto> allSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<String> chooseSubscription(@PathVariable("id") Long id) {
        subscriptionService.chooseSubscription(id);
        return new ResponseEntity<>("You chose subscription " + id, HttpStatus.OK);
    }

    @PostMapping("/payment")
    public String paymentForSubscription(HttpServletRequest request) throws PayPalRESTException {
        return subscriptionService.paymentForSubscription(request);
    }

    @PostMapping("/change/{id}")
    public ResponseEntity<String> changeSubscription(@PathVariable("id") Long id) {
        subscriptionService.changeSubscription(id);
        return new ResponseEntity<>("You changed your subscription to id " + id , HttpStatus.OK);
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