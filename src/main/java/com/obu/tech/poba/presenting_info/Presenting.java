package com.obu.tech.poba.presenting_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ หัวข้อเรื่อง / บทความ")
    private String presentTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อการประชุม")
    private String presentName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงานที่จัด")
    private String presentInstitution;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อแหล่งเงินทุน")
    private String presentFund;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนเงิน")
    private Double presentAmount;

    @NotNull(message = "โปรดเลือก กำหนดการเริ่มต้น")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate presentDateStart;

    @NotNull(message = "โปรดเลือก กำหนดการสิ้นสุด")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate presentDateEnd;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ระดับกิจกรรม")
    private String presentLevel;
}
