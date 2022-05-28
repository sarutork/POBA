package com.obu.tech.poba.consultant_info.thesis;

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
@Table(name = "consultant_thesis", schema = "poba")
public class ConsultantThesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long thesisId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ตำแหน่งอาจารย์ที่ปรึกษา")
    private String consultantPosition;

    @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เลขประจำตัวนิสิต")
    public String studentsId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้านิสิต")
    private String studentPrefix;
    private String studentPrefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล นิสิต")
    private String studentName;
    private String studentSurname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ระดับการศึกษา")
    private String studentLevel;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หลักสูตร")
    private String courseName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ประเภท")
    private String thesisType;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หัวข้อ")
    private String thesisTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ครั้งที่พิจารณาอนุมัติ")
    private String thesisConsider;

    @NotNull(message = "โปรดเลือก วันที่เปิดเล่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisStartdate;

    @NotNull(message = "โปรดเลือก วันที่ปิดเล่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisEnddate;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ครั้งที่รับทราบผลการสอบ")
    private String thesisApprove;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ครั้งที่อนุมัติผู้สำเร็จการศึกษา")
    private String thesisSuccess;

    @NotNull(message = "โปรดเลือก วันที่อนุมัติผู้สำเร็จการศึกษา")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisSuccessDate;

    @Size(min = 1, max = 255, message = "โปรดเลือก ผลประเมิน")
    private String thesisAssessment;
}
