package com.obu.tech.poba.consultant_info.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsultantDto {
    private String prefix;
    private String prefixOther;
    private String name;
    private String surname;
    private String studentsLevel;
    private String admissionStatus;
    private String yearOfStudy;
    private String yearStart;
    private String yearEnd;
    //private Map<Integer, Integer> yearlyStdCount;
    private String sumYear1;
    private String sumYear2;
    private String sumYear3;
    private String sumYear4;
    private String sumYear5;
    private String sumYear6;
    private String sumYear7;
    private String sumYear8;
    private String sumYear9;
    private String sumYear10;
}
