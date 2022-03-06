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

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-หัวข้อเรื่อง")
    private String publishedTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อวารสาร")
    private String publishedJournal;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ปีที่")
    private String publishedYear;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ฉบับ")
    private String publishedIssue;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล เดือน")
    private String publishedMonth;

    @Size(min = 1, max = 255, message = "โปรดเลือก ปี")
    private String publishedYear2;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ฐาน")
    private String publishedBase;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับกิจกรรม")
    private String publishedLevel;

    private long publishedJoinId;
    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า ผู้เขียนร่วม")
    private String publishedJoinPrefix;
    private String publishedJoinPrefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล ผู้เขียนร่วม")
    private String publishedJoinName;
    private String publishedJoinSurname;

    @NotNull(message="กรุณาตรวจสอบข้อมูล เงินสนับสนุน")
    @DecimalMin(value = "0", message="กรุณาตรวจสอบข้อมูล เงินสนับสนุน")
    private Double publishedFund;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ไตรมาส 1")
    private int publishedQ1;
    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ไตรมาส 2")
    private int publishedQ2;
    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ไตรมาส 3")
    private int publishedQ3;
    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล ไตรมาส 4")
    private int publishedQ4;
}
