package com.fyp.bookshare.controller;

import com.fyp.bookshare.pojo.Donations;
import com.fyp.bookshare.service.admin.IDonationsService;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author o0wen0o
 * @create 2024-04-13 10:30 AM
 */
@RestController
@Slf4j
@RequestMapping("/api/stripe-webhook")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Resource
    private IDonationsService donationsService;

    @PostMapping("/stripe/events")
    public String handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        if (sigHeader == null) {
            return "";
        }

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

        } catch (JsonSyntaxException e) {
            // Invalid payload
            log.error("Webhook error while parsing payload.");
            return "";
        } catch (SignatureVerificationException e) {
            // Invalid signature
            log.error("Webhook error while validating signature.");
            return "";
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                Map<String, String> metadata = paymentIntent.getMetadata();
                log.info("PaymentIntent was successful: {}", metadata);

                BigDecimal donation_amount = BigDecimal.valueOf(paymentIntent.getAmount() / 100);
                Integer userId = Integer.parseInt(metadata.get("userId"));
                Integer fundraisingProjectId = Integer.parseInt(metadata.get("fundraisingProjectId"));

                Donations donation = new Donations(null, donation_amount, null, userId, fundraisingProjectId);
                boolean result = donationsService.addDonation(donation);
                break;

            default:
                log.warn("Unhandled event type: {}", event.getType());
                break;
        }

        return "";
    }
}
