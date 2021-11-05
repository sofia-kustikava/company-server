package com.example.companyserver.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayPalServiceTest {

    @Autowired
    private PayPalService payPalService;

    @Test
    public void createPaymentTest() throws PayPalRESTException {

        Payment expected = payPalService.createPayment(1L, 100.00, "Description");
        assertTrue(expected.getLinks()
                .stream()
                .map(Links::getRel)
                .collect(Collectors.toList()).contains("approval_url"));
    }
}
