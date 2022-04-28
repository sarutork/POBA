package com.obu.tech.poba.authenticate;

import lombok.Data;

@Data
public class ResetPassword {
    private String newPassword,confirmPassword;
}
