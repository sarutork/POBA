package com.obu.tech.poba.user_management;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class UserDto {
    private long id;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล Username")
    private String username;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล Password")
    private String password;

    @Size(min = 1, max = 255, message = "โปรดเลือก role")
    private String role;
}
