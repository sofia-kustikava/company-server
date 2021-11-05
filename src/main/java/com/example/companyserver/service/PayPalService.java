package com.example.companyserver.service;

import com.example.companyserver.exceptions.PayPalException;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayPalService {
    @Value("${paypal.success.url}")
    private String successUrl;
    @Value("${paypal.cancel.url}")
    private String cancelUrl;
    @Value("${paypal.currency}")
    private String currency;
    @Value("${paypal.method}")
    private String method;
    @Value("${paypal.intent}")
    private String intent;

    private final APIContext apiContext;

    public Payment createPayment(
            Long id,
            Double total,
            String description) throws PayPalRESTException{
        try {
            Amount amount = new Amount();
            amount.setCurrency(currency);
            total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
            amount.setTotal(String.format("%.2f", total));

            Transaction transaction = new Transaction();
            transaction.setDescription(description);
            transaction.setAmount(amount);

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod(method);

            Payment payment = new Payment();
            payment.setPayer(payer);
            payment.setIntent(intent);
            payment.setTransactions(transactions);
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl + id);
            payment.setRedirectUrls(redirectUrls);
            return payment.create(apiContext);
        } catch (PayPalRESTException e) {
            log.info("There are some problems with PayPal");
            throw new PayPalException("PayPal isn't working");
        }
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        try {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            return payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException e) {
            log.info("There are some problems with PayPal");
            throw new PayPalException("PayPal isn't working");
        }
    }
}
