package com.obu.tech.poba.published_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublishedDto {
    private long publishedId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะผู้เขียน")
    private String publishedStatus;

    private String publishedType;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-หัวข้อเรื่อง")
    private String publishedTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อวารสาร")
    private String publishedJournal;

    private String publishedYear;
    private String publishedIssue;
    private String publishedPage;
    private String publishedMonth;
    private String monthOther;


    @Size(min = 1, max = 255, message = "โปรดเลือก ปี")
    private String publishedYear2;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ฐาน")
    private String publishedBase;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับกิจกรรม")
    private String publishedLevel;

    private long publishedJoinId;
    private String publishedJoinPrefix;
    private String publishedJoinPrefixOther;
    private String publishedJoinName;
    private String publishedJoinSurname;
    private Double publishedFund;
    private int publishedQ1;
    private int publishedQ2;
    private int publishedQ3;
    private int publishedQ4;
}
