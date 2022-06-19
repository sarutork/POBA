package com.obu.tech.poba.teaching_info;

import com.obu.tech.poba.utils.upload.Upload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "teach_info", schema = "poba")
public class Teaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;

    @Size(min = 1, max = 255, message = "โปรดเลือก ปีการศึกษา")
    public String studyYear;

    @NotBlank(message = "โปรดเลือก ภาคการศึกษา")
    public String semester;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระบบ")
    public String studyType;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล รหัสวิชา")
    public int subjectId;

    @Size(min =1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อย่อวิชา")
    public String subjectName;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล รหัสวิชา")
    public int subjectCredit;

    @PositiveOrZero(message="กรุณาตรวจสอบข้อมูล รหัสวิชา")
    public int currentCredit;

    @Size(min = 1, max = 255, message = "โปรดเลือก วิธีสอน")
    public String teachType;

    @Size(min = 1, max = 255, message = "โปรดเลือก วัน")
    public String teachDay;

    @Size(min = 1, max = 5, message = "โปรดเลือก เวลาเรียนเริ่มต้น")
    public String studyStart;

    @Size(min = 1, max = 5, message = "โปรดเลือก เวลาเรียนสิ้นสุด")
    public String studyEnd;

    @Size(min = 1, message = "โปรดเลือก อาคาร")
    public String teachLocation;
    public String teachLocationOther;

    @Size(min = 1, message = "โปรดเลือก ห้อง")
    public String teachRoom;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    public String prefix;
    public String prefixOther;
    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    public String name;
    public String surname;

    @Size(min = 1, max = 255, message = "โปรดเลือก สถานะการสอน")
    public String teachStatus;

    public String institutionInfo;
    public String teachTopic;
    public int teachTimes;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate teachDate;
    public String noteOfTeach;

    @Size(min = 1, max = 255, message = "โปรดเลือก รูปแบบการสอน")
    public String teachStyle;

    @Size(min = 1, max = 255, message = "โปรดเลือก ช่องทางติดต่ออาจารย์ผู้สอน")
    public String teachStyleDetail;
    public String teachStyleDetailOther;

    @Pattern(regexp = "^\\d+$",message="กรุณาตรวจสอบข้อมูล จำนวนนิสิตรับได้ทั้งหมด")
    public String totalOfStudents;

    @Pattern(regexp = "^\\d+$",message="กรุณาตรวจสอบข้อมูล จำนวนนิสิตลงทะเบียนเรียน")
    public String totalStudentsRegister;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate midtermExamDate;

    public String midtermExamTimeStart;

    public String midtermExamTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate finalExamDate;

    public String finalExamTimeStart;

    public String finalExamTimeEnd;

    @Transient
    private List<Upload> uploads;

    @Transient
    @ToString.Exclude
    private Long[] filesToKeep;

    @Transient
    @ToString.Exclude
    private MultipartFile[] newFiles;

}
