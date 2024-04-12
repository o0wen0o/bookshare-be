package com.fyp.bookshare.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author o0wen0o
 * @create 2024-04-12 6:31 PM
 */
@Configuration
public class StripeConfig {

    @Value("${stripe.api.secret.key}")
    private String apiKey;

    @PostConstruct
    public void setup() {
        Stripe.apiKey = apiKey;
    }
}
