package com.fyp.bookshare.entity.vo.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Confirm password reset
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmResetVO {

    @Email
    String email;

    @Length(max = 6, min = 6)
    String code;
}
