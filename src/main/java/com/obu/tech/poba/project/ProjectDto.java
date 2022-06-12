package com.obu.tech.poba.project;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ProjectDto{
    private long projectId;

    @Size(min = 1, max = 255, message = "โปรดเลือก ปีการศึกษา")
    private String projectYear;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อโครงการ/กิจกรรม")
    private String projectName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงานที่รับผิดชอบ")
    private String projectInstitution;

    @Size(min = 1, max = 255, message = "โปรดเลือก ประเภทกิจกรรม")
    private String projectType;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล งบประมาณ (ตั้งงบ)")
    private double projectBudget;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล งบประมาณ (อนุมัติ)")
    private double projectBudgetApprove;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนผู้เข้าร่วมกิจกรรม (เป้า)")
    private int projectPersonTarget;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล จำนวนผู้เข้าร่วมกิจกรรม (เข้าร่วมจริง)")
    private int projectPersonActual;

    @NotNull(message = "โปรดเลือก ระยะเวลาเตรียมกิจกรรม เริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectPreDateFrom;

    @NotNull(message = "โปรดเลือก ระยะเวลาเตรียมกิจกรรม ถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectPreDateTo;

    @NotNull(message = "โปรดเลือก วันที่จัดกิจกรรม เริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectDateFrom;

    @NotNull(message = "โปรดเลือก วันที่จัดกิจกรรม ถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectDateTo;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สถานที่จัดโครงการ")
    private String projectLocation;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ลักษณะกิจกรรม/โครงการ")
    private String projectKind;

    private List<Participant> participants;
}
