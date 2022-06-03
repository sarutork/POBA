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

    @Size(min = 1, max = 255, message = "โปรดเลือก ตำแหน่งอาจารย์ที่ปรึกษา")
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

    private String thesisType;
    private String thesisTopic;
    private String thesisConsider;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisStartdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisEnddate;
    private String thesisApprove;
    private String thesisSuccess;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisSuccessDate;
    private String thesisAssessment;
}
