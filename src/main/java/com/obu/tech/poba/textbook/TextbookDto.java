package com.obu.tech.poba.textbook;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TextbookDto {
    private long textbookId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "โปรดเลือก ประเภทผลงาน")
    private String textbookType;

    @Size(min = 1, max = 255, message = "รหัสประกาศ/รหัสสัญญา")
    private String textbookAnnounce;

    @Size(min = 1, max = 255, message = "รหัสสัญญา")
    private String textbookContract;

    @Size(min = 1, max = 255, message = "ชื่อ/หัวข้อ")
    private String textbookTopic;

    @Size(min = 1, max = 255, message = "แหล่งเงินทุนวิจัย")
    private String textbookFund;

    @NotNull(message="กรุณาตรวจสอบข้อมูล งวดที่")
    private List<TextbookPhase> phases;

    private int textbookAmountTotal;

    @NotNull(message = "โปรดเลือก ระยะเวลาเริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookDateFrom;

    @NotNull(message = "โปรดเลือก ระยะเวลาถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookDateTo;

    @NotNull(message = "โปรดเลือก ขยายวัน เดือน ปี")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate;

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

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล นำไปอ้างอิง")
    private String textbookRef;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล TCI")
    @Column(name = "textbook_tci")
    private String textbookTCI;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะ")
    private String textbookLevel;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ไตรมาส 1")
    @Column(name = "textbook_q1")
    private String textbookQ1;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ไตรมาส 2")
    @Column(name = "textbook_q2")
    private String textbookQ2;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ไตรมาส 3")
    @Column(name = "textbook_q3")
    private String textbookQ3;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ไตรมาส 4")
    @Column(name = "textbook_q4")
    private String textbookQ4;
}
