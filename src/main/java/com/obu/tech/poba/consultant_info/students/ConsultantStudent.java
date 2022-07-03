package com.obu.tech.poba.consultant_info.students;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "consultant_students", schema = "poba")
public class ConsultantStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long consultantStudentId;

    @NotEmpty(message = "โปรดเลือก คำนำหน้า และ ชื่อ-นามสกุล ที่ปรึกษา")
    private String persNo;
    @Transient
    private String prefix;
    @Transient
    private String name;

    @Size(min = 1, max = 11, message = "โปรดตรวจสอบ เลขประจำตัว, คำนำหน้า และ ชื่อ-นามสกุล นิสิต")
    public String studentsId;
    @Transient
    public String studentPrefix;
    @Transient
    public String studentName;
    @Transient
    public String yearOfStudy;
    @Transient
    public String admissionStatus;
    @Transient
    public String studentsLevel;
    @Transient
    public String course;

    @Size(min = 1, max = 255, message = "โปรดเลือก ภาควิชา")
    public String department;
}
