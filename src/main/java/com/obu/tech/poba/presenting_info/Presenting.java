package com.obu.tech.poba.presenting_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "present_work", schema = "poba")
public class Presenting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long presentId;

    @NotEmpty(message = "โปรดเลือก คำนำหน้า และ ชื่อ-นามสกุล ")
    private String persNo;

    @Transient
    private String prefix;
    @Transient
    private String name;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ หัวข้อเรื่อง / บทความ")
    private String presentTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อการประชุม")
    private String presentName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงานที่จัด")
    private String presentInstitution;

    private String presentCountry;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงาน")
    private String presentFund;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนเงิน")
    private Double presentAmount;

    private String presentFund2;
    private Double presentAmount2;

    @NotNull(message = "โปรดเลือก กำหนดการเริ่มต้น")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate presentDateStart;

    @NotNull(message = "โปรดเลือก กำหนดการสิ้นสุด")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate presentDateEnd;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ระดับกิจกรรม")
    private String presentLevel;
}
