package com.obu.tech.poba.teaching_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

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
    public Timestamp studyStart;
    public Timestamp studyEnd;
    public String teachLocation;
    public String teachRoom;
    public String prefix;
    public String name;
    public String surname;
    public String teachStatus;
    public String institutionInfo;
    public String teachTopic;
    public int teachTimes;
    public Timestamp teachDate;
    public String noteOfTeach;
    public String teachStyle;
    public String teachStyleDetail;
    public String totalOfStudents;
    public String totalStudentsRegister;
    public Timestamp midtermExamDate;
    public Time midtermExamTime;
    public Timestamp finalExamDate;
    public Time finalExamTime;


}
