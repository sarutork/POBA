package com.obu.tech.poba.press_info;

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
@Table(name = "press_info", schema = "poba")
public class Press {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pressId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อผลงานความรู้ที่เผยแพร่")
    private String pressName;

    @NotNull(message = "โปรดเลือก วันที่เผยแพร่")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pressDate;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล รูปแบบเนื้อหาที่ปรากฎในสื่อ")
    private String pressTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล เว็บไซต์อ้างอิง")
    private String pressSiteRef;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล [ช่องทาง] สิ่งพิมพ์")
    private String pressPrint;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล [ช่องทาง] ทีวีดิจิทัล")
    private String pressTv;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล [ช่องทาง] cable/satellite")
    private String pressSatellite;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล [ช่องทาง] ออนไลน์")
    private String pressOnline;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า แขกรับเชิญ1")
    private String guestPrefix1;
    private String guestPrefixOther1;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล แขกรับเชิญ1")
    private String guestName1;
    private String guestSurname1;

    private String guestPrefix2;
    private String guestPrefixOther2;

    private String guestName2;
    private String guestSurname2;

    private String guestPrefix3;
    private String guestPrefixOther3;

    private String guestName3;
    private String guestSurname3;

}
