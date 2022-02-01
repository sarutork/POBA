package com.obu.tech.poba.teaching_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "teach_info", schema = "poba")
public class Teaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;
    public String studyYear;
    public String semester;
    public String studyType;
    public int subjectId;
    public String subjectName;
    public int subjectCredit;
    public int currentCredit;
    public String teachType;
    public String teachDay;
    public String studyStart;
    public String studyEnd;
    public String teachLocation;
    public String teachRoom;
    public String prefix;
    public String prefixOther;
    public String name;
    public String surname;
    public String teachStatus;
    public String institutionInfo;
    public String teachTopic;
    public int teachTimes;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate teachDate;
    public String noteOfTeach;
    public String teachStyle;
    public String teachStyleDetail;
    public String totalOfStudents;
    public String totalStudentsRegister;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate midtermExamDate;

    public String midtermExamTimeStart;

    public String midtermExamTimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate finalExamDate;

    public String finalExamTimeStart;

    public String finalExamTimeEnd;

}
