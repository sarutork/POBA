package com.obu.tech.poba.personnel_info.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "profile", schema = "poba")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;

    @Size(min = 1, max = 11, message="กรุณาตรวจสอบข้อมูล Pers. No.")
    private String persNo;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล อีเมล จุฬาฯ")
    private String emailOrg;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล อีเมล ส่วนตัว")
    private String emailPersonal;

    @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เบอร์ห้องทำงาน")
    private String tel;

    @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เบอร์มือถือ")
    private String mobile;

    @NotNull(message = "โปรดเลือก วันที่เข้าจุฬา")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startWorkDate;

    @NotNull(message = "โปรดเลือก วันที่นับอายุงาน")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startCountWorkDate;

    @NotNull(message = "โปรดเลือก วันที่เกษียณอายุ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toWorkDate;

    private int totalWorkYear;
    private int totalWorkMonth;
    private int totalWorkDay;

    private double totalWorkOHEC;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับการศึกษา สกอ.")
    private String ohecEducationLevel;

    @Size(min = 1, max = 255, message = "โปรดเลือก โครงสร้าง สกอ.")
    private String ohecStructure;

    @Size(min = 1, max = 255, message = "โปรดเลือก สาย")
    private String section;

    @Size(min = 1, max = 255, message = "โปรดเลือก กลุ่มตำแหน่ง สกอ.")
    private String ohecPositionGroup;

    private String ohecAcademicPosition;

    @Size(min = 1, max = 255, message = "โปรดเลือก ชื่อโครงสร้างระดับ1")
    private String structureNameLevel1;

    @Size(min = 1, max = 255, message = "โปรดเลือก ชื่อโครงสร้างระดับ2")
    private String structureNameLevel2;

    private String structureNameLevel3;

    private String structureNameLevel4;

    @Size(min = 1, max = 255, message = "โปรดเลือก กลุ่มพนักงาน")
    private String staffGroup;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล กลุ่มพนักงานย่อย")
    private String staffSubGroup;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ตำแหน่ง")
    private String position;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล งาน")
    private String work;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สัญญาการจ้างงาน")
    private String empContract;

    private String subSectionScopeOfWork;
}
