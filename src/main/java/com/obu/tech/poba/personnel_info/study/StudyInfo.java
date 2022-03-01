package com.obu.tech.poba.personnel_info.study;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "study_info", schema = "poba")
public class StudyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    public String prefix;

    @Size(min = 1, max = 255, message = "โปรดป้อน ชื่อ (1 - 255)")
    public String name;
    public String surname;

    @Size(min = 1, max = 255, message = "โปรดป้อน คำสั่งเดินทาง")
    public String travelOrder;

    @NotNull(message = "โปรดเลือก วันที่เริ่มต้น")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;

    @NotNull(message = "โปรดเลือก วันที่สิ้นสุด")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;

    public int totalYear;
    public int totalMonth;
    public int totalDay;

    @Size(min = 1, max = 255, message = "โปรดป้อน รายละเอียดกิจกรรม (1 - 255)")
    public String activityDetail;

    @Size(min = 1, max = 255, message = "โปรดป้อน มหาวิทยาลัย/สถาบันที่ไป (1 - 255)")
    public String location;

    @Size(min = 1, max = 255, message = "โปรดเลือก ประเภท")
    public String locationType;

    @Size(min = 1, max = 255, message = "โปรดป้อน ชื่อประเทศ (1 - 255)")
    public String country;

    @Size(min = 1, max = 255, message = "โปรดป้อน แหล่งทุน  (1 - 255)")
    public String fund;
}
