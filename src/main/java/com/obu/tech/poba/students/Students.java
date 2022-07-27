package com.obu.tech.poba.students;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "students", schema = "poba")
public class Students {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long Id;

  @Size(min = 1, max = 10, message = "กรุณาตรวจสอบข้อมูล เลขประจำตัวนิสิต")
  private String studentsId;

  @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้านิสิต")
  private String studentsPrefix;
  private String studentsPrefixOther;

  @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-สกุล")
  private String studentsName;
  private String studentsSurname;

  @Size(min = 1, max = 255, message = "โปรดเลือก เข้าศึกษาปีการศึกษา")
  private String studentsYear;

  @Size(min = 1, max = 255, message = "โปรดเลือก วิธีรับเข้า")
  private String studentsHow;

  @Size(min = 1, max = 255, message = "โปรดเลือก ระดับการศึกษา")
  private String studentsLevel;

  @Size(min = 1, max = 255, message = "โปรดเลือก หลักสูตร")
  private String studentsCourse;

  @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หมายเลขโทรศัพท์")
  private String studentsTel;

  private String studentsTelemergency;
  private String studentsRelation;
  private String studentsRelationOther;
  private String studentsEmail;

  @NotEmpty(message = "โปรดเลือก คำนำหน้า และ ชื่อ-นามสกุล ")
  private String persNo;

  @Transient
  private String prefix;
  @Transient
  private String name;

  private String studentsStatus;
  private String studentsSuccess;
  private String studentsOutreason;
  private String studentsRetryreason;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate studentsUpdate;
}
