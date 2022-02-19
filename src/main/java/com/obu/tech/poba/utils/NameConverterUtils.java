package com.obu.tech.poba.utils;

import org.springframework.stereotype.Service;

@Service
public class NameConverterUtils {
    public String[] fullNameToNameNSurname(String fullNameStr){
        String fullName = fullNameStr.replaceAll("\\s+", " ").trim();
        int firstSpace = fullName.contains(" ") ? fullName.indexOf(" ") : fullName.length();

        String[] nameArr = new String[2];
        nameArr[0] = fullName.substring(0, firstSpace);
        nameArr[1] = fullName.substring(firstSpace).trim();

        return nameArr;
    }
}
