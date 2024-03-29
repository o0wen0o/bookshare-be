package com.fyp.bookshare.entity.vo.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 确认重置密码
 */
@Data
@AllArgsConstructor
public class ConfirmResetVO {

    @Email
    String email;

    @Length(max = 6, min = 6)
    String code;
}
