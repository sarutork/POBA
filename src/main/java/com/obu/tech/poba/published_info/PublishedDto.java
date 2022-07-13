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

    private String publishedStatus;

    private String publishedStatusOther;

    private String publishedType;

    private String publishedTypeOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-หัวข้อเรื่อง")
    private String publishedTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อวารสาร")
    private String publishedJournal;

    private String publishedYear;
    private String publishedIssue;
    private String publishedPage;
    private String publishedMonth;
    private String monthOther;


    @Size(min = 1, max = 255, message = "โปรดเลือก ปีพิมพ์")
    private String publishedYear2;

    private String publishedBase;
    private String publishedLevel;

    private long publishedJoinId;
    private String publishedJoinPrefix;
    private String publishedJoinPrefixOther;
    private String publishedJoinName;
    private String publishedJoinSurname;
    private String publishedJoinPrefix2;
    private String publishedJoinPrefixOther2;
    private String publishedJoinName2;
    private String publishedJoinSurname2;
    private String publishedJoinPrefix3;
    private String publishedJoinPrefixOther3;
    private String publishedJoinName3;
    private String publishedJoinSurname3;
    private boolean otherPeople;
    private Double publishedFund;
    private int publishedQ1;
    private int publishedQ2;
    private int publishedQ3;
    private int publishedQ4;
    private String fiscalYearQ1;
    private String fiscalYearQ2;
    private String fiscalYearQ3;
    private String fiscalYearQ4;

}
