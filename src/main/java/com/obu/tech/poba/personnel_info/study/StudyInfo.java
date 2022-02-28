package com.obu.tech.poba.personnel_info.study;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    public String prefix;

    @Size(min = 1, max = 255, message = "โปรดป้อน ชื่อ (1 - 255)")
    public String name;
    public String surname;

    @Size(min = 1, max = 255, message = "โปรดป้อน คำสั่งเดินทาง")
    public String travelOrder;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;

    public int totalYear;
    public int totalMonth;
    public int totalDay;

    @Size(min = 1, max = 255, message = "โปรดป้อน รายละเอียดกิจกรรม")
    public String activityDetail;

    @Size(min = 1, max = 255, message = "โปรดป้อน รายละเอียดกิจกรรม")
    public String location;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    public String locationType;

    @Size(min = 1, max = 255, message = "โปรดป้อน ชื่อประเทศ")
    public String country;

    @Size(min = 1, max = 255, message = "โปรดป้อน แหล่งทุน")
    public String fund;
}
