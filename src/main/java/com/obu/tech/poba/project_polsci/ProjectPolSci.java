package com.obu.tech.poba.project_polsci;

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
@Table(name = "project_polsci", schema = "poba")
public class ProjectPolSci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long polsciId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะ ข้อมูลอาจารย์")
    private String polsciStaffType;
    private String polsciStaffTypeOther;

    @Size(min = 1, max = 255, message = "โปรดเลือก ปีการศึกษา")
    private String polsciYear;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อโครงการ/กิจกรรม")
    private String polsciName;

    @NotNull(message = "โปรดเลือก ระยะเวลาเตรียมกิจกรรม เริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate polsciPreDateFrom;

    @NotNull(message = "โปรดเลือก ระยะเวลาเตรียมกิจกรรม ถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate polsciPreDateTo;

    @NotNull(message = "โปรดเลือก วันที่จัดกิจกรรม เริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate polsciDateFrom;

    @NotNull(message = "โปรดเลือก วันที่จัดกิจกรรม ถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate polsciDateTo;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สถานที่จัดโครงการ")
    private String polsciLocation;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะ ตำแหน่งกิจกรรม")
    private String polsciStatus;
    private String polsciStatusOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน้าที่รับผิดชอบ")
    private String polsciPosition;

    @PositiveOrZero(message = "กรุณาตรวจสอบข้อมูล จำนวนนิสิตปฏิบัติงาน")
    private int polsciTotalOperation;

    @PositiveOrZero(message = "กรุณาตรวจสอบข้อมูล จำนวนนิสิตเข้าร่วม")
    private int polsciTotalAttend;

    @PositiveOrZero(message = "กรุณาตรวจสอบข้อมูล ชั่วโมง (กิจกรรม)")
    private int polsciTotalHour;

    @Size(min = 1, max = 255,message = "กรุณาตรวจสอบข้อมูล เอกสารอ้างอิง/เผยแพร่")
    private String polsciDocRef;

    @Size(min = 1, max = 255,message = "กรุณาตรวจสอบข้อมูล หมายเหตุ")
    private String noteOfPolsci;
}
