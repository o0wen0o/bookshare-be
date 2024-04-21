package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.CreatePayment;
import com.fyp.bookshare.entity.dto.CreatePaymentResponse;
import com.google.gson.Gson;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;

/**
 * @author o0wen0o
 * @create 2024-04-12 5:31 PM
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final Gson gson = new Gson();

    @PostMapping("/create-payment-intent")
    public RestBean<String> createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setCurrency("myr")
                        .putMetadata("userId", createPayment.getUserId())
                        .putMetadata("fundraisingProjectId", createPayment.getFundraisingProjectId())
                        .setAmount(createPayment.getAmount() * 100L)
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
        return RestBean.success(gson.toJson(paymentResponse));
    }
}
