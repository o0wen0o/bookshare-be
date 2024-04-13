package com.fyp.bookshare.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author o0wen0o
 * @create 2024-04-12 5:42 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayment {

    private Integer amount;

    private String userId;

    private String fundraisingProjectId;
}
