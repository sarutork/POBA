package com.obu.tech.poba.academic_service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "academic_service", schema = "poba")
public class AcademicService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long serviceId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สถานะ")
    private String serviceStatus;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล เลขที่คำสั่ง")
    private String serviceOrder;

    @NotNull(message = "โปรดเลือก วันที่คำสั่งเริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate serviceDateFrom;

    @NotNull(message = "โปรดเลือก วันที่คำสั่งถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate serviceDateTo;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หมายเหตุ")
    private String noteOfService;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ข้อมูลตำแหน่งบริหาร")
    private String servicePosition;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อหน่วยงานที่เป็นกรรมการ/บริหาร")
    private String serviceInstitution;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ระดับกิจกรรม")
    private String serviceLevel;

}
