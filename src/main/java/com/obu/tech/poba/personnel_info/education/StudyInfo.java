package com.obu.tech.poba.personnel_info.education;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "study_info", schema = "poba")
public class StudyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;
    public String prefix;
    public String name;
    public String surname;
    public String travelOrder;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;
    public int totalDate;
    public String activityDetail;
    public String location;
    public String locationType;
    public String country;
    public String fund;
}
