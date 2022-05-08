package com.obu.tech.poba.textbook;

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
@Table(name = "textbook", schema = "poba")
public class Textbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long textbookId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "โปรดเลือก ประเภทผลงาน")
    private String textbookType;

    private String textbookAnnounce;
    private String textbookContract;

    @Size(min = 1, max = 255, message = "ชื่อ/หัวข้อ")
    private String textbookTopic;

    @Size(min = 1, max = 255, message = "แหล่งเงินทุนวิจัย")
    private String textbookFund;

    private double textbookAmountTotal;

    @NotNull(message = "โปรดเลือก ระยะเวลาเริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookDateFrom;

    @NotNull(message = "โปรดเลือก ระยะเวลาถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookDateTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate3;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะ")
    private String textbookStatus;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ประเภท")
    private String textbookPbType;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ครั้งที่")
    private String textbookIssue;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ปีที่")
    private String textbookYear;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล สำนัก")
    private String textbookInstitution;

    private String textbookDiffText;

    private String textbookRef;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล TCI")
    @Column(name = "textbook_tci")
    private String textbookTCI;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะ")
    private String textbookLevel;
}
