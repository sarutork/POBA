package com.obu.tech.poba.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class StudyInfo {
    public int staffId;
    public String prefix;
    public String name;
    public String surname;
    public String travelOrder;
    public Timestamp startDate;
    public Timestamp endDate;
    public int totalDate;
    public String activityDetail;
    public String location;
    public String locationType;
    public String country;
    public String fund;
}
