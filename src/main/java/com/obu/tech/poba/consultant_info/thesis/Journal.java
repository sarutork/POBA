package com.obu.tech.poba.consultant_info.thesis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "journal_info", schema = "poba")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long journalId;
    private long thesisId;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ลักษณะ")
    private String journalStyle;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หัวข้อ")
    private String journalTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อวารสาร")
    private String journalName;

    @Size(min = 1, max = 255, message = "โปรดเลือก ปีที่ตีพิมพ์")
    private String journalPublicYear;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ฉบับ")
    private String journalIssue;

    @Size(min = 1, max = 255, message = "โปรดเลือก พ.ศ.")
    private String journalYear;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ฐานข้อมูล")
    private String journalDatabase;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ระดับ")
    private String journalLevel;

}
