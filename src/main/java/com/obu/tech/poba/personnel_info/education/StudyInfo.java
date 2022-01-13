package com.obu.tech.poba.personnel_info.education;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "study_info", schema = "poba")
public class StudyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int staffId;
    public String prefix;
    public String name;
    public String surname;
    public String travelOrder;
    public LocalDate startDate;
    public LocalDate endDate;
    public int totalDate;
    public String activityDetail;
    public String location;
    public String locationType;
    public String country;
    public String fund;
}
