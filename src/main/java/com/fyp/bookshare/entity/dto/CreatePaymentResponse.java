package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author o0wen0o
 * @create 2024-04-12 5:48 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentResponse {

    private String clientSecret;
}


