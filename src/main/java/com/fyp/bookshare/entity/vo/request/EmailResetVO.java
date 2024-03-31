package com.fyp.bookshare.entity.vo.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Password reset form entity
 */
@Data
public class EmailResetVO {

    @Email
    String email;

    @Length(max = 6, min = 6)
    String code;

    @Length(min = 6, max = 20)
    String password;
}
