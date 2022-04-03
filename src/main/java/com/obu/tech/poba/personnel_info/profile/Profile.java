package com.obu.tech.poba.personnel_info.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "profile", schema = "poba")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล อีเมล จุฬาฯ")
    private String emailOrg;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล อีเมล ส่วนตัว")
    private String emailPrivate;

    @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เบอร์ห้องทำงาน")
    private String tel;

    @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เบอร์มือถือ")
    private String mobile;

    private LocalDate startWorkDate;

    private LocalDate startCountWorkDate;

    private LocalDate startCountWorkOHECDate;

    private LocalDate toWorkDate;


}
