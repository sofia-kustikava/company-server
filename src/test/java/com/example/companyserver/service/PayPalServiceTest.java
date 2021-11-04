package com.example.companyserver.service;

import com.paypal.base.rest.APIContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PayPalServiceTest {

    @Mock
    private APIContext apiContext;

    @InjectMocks
    private PayPalService payPalService;

    @BeforeEach
    public void beforeTest() {
    }

    @Test
    public void createPaymentTest() {

    }

    @Test
    public void executePaymentTest() {

    }
}
