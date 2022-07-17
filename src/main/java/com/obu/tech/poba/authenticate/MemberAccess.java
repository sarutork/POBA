package com.obu.tech.poba.authenticate;

import lombok.Data;

import java.util.List;

@Data
public class MemberAccess {
    private POBAUser member;
    private List<String> roles;
}
