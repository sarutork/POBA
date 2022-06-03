package com.obu.tech.poba.consultant_info.students;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "consultant_students", schema = "poba")
public class ConsultantStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    public String prefix;
    public String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    public String name;
    public String surname;

    @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เลขประจำตัวนิสิต")
    public String studentsId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้านิสิต")
    public String studentPrefix;
    public String studentPrefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล นิสิต")
    public String studentName;
    public String studentSurname;

    @Size(min = 1, max = 255, message = "โปรดเลือก ปีที่เข้าศึกษา")
    public String yearOfStudy;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับการศึกษา")
    public String admissionStatus;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะการรับเข้า")
    public String studentsLevel;

    @Size(min = 1, max = 255, message = "โปรดเลือก หลักสูตร")
    public String course;

    @Size(min = 1, max = 255, message = "โปรดเลือก ภาควิชา")
    public String department;
}
