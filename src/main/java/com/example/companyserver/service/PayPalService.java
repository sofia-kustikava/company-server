package com.example.companyserver.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayPalService {
    public static final String SUCCESS_URL = "/subscription/success";
    public static final String CANCEL_URL = "/subscription/cancel";

    private final APIContext apiContext;

    public Payment createPayment(
            Double total,
            String description) throws PayPalRESTException{
        Amount amount = new Amount();
        amount.setCurrency("USD");
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("PAYPAL");

        Payment payment = new Payment();
        payment.setPayer(payer);
        payment.setIntent("SALE");
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/" + CANCEL_URL);
        redirectUrls.setReturnUrl("http://localhost:8080/" + SUCCESS_URL);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(Long paymentId, Long payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId.toString());
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId.toString());
        return payment.execute(apiContext, paymentExecute);
    }
}
