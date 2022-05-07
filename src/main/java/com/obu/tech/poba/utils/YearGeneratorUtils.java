package com.obu.tech.poba.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class YearGeneratorUtils {

    @Value("${poba.years.bfcurrent}")
    private String BEFORE_CURRENT;

    @Value("${poba.years.afcurrent}")
    private String AFTER_CURRENT;

    public List genYears(int bfCurrnt,int afCurrnt){

        int year = Calendar.getInstance().get(Calendar.YEAR);

        year += 543;

        List<Integer> yearList = new ArrayList();

        yearList.add(year);

        for(int i = 1; i <= bfCurrnt; i++){
            yearList.add(year-i);
        }

        for(int i = 1; i <= afCurrnt; i++){
            yearList.add(year+i);
        }

        Collections.sort(yearList);

        return yearList;
    }

    public List genYears(){

        int bfCurrnt = Integer.parseInt(BEFORE_CURRENT);
        int afCurrnt = Integer.parseInt(AFTER_CURRENT);


        int year = Calendar.getInstance().get(Calendar.YEAR);

        year += 543;

        List<Integer> yearList = new ArrayList();

        yearList.add(year);

        for(int i = 1; i <= bfCurrnt; i++){
            yearList.add(year-i);
        }

        for(int i = 1; i <= afCurrnt; i++){
            yearList.add(year+i);
        }

        Collections.sort(yearList);

        return yearList;
    }

}
