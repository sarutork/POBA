package com.obu.tech.poba.utils;

import org.springframework.stereotype.Service;

@Service
public class CommonUtils {
    public String chkNullStrObj(Object e){
        return e != null? e.toString(): "";
    }
}
