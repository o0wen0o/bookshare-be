package com.fyp.bookshare.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author o0wen0o
 * @create 2024-04-11 11:24 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundraisingProjectDetailDTO {

    private String projectName;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal goalAmount;

    private BigDecimal amountRaised;

    private String donationCount;

    private String username;

    private String imgUrl;
}
