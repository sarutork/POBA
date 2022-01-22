package com.obu.tech.poba.teaching_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
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
    public LocalDate studyStart;
    public LocalDate studyEnd;
    public String teachLocation;
    public String teachRoom;
    public String prefix;
    public String name;
    public String surname;
    public String teachStatus;
    public String institutionInfo;
    public String teachTopic;
    public int teachTimes;
    public LocalDate teachDate;
    public String noteOfTeach;
    public String teachStyle;
    public String teachStyleDetail;
    public String totalOfStudents;
    public String totalStudentsRegister;
    public LocalDate midtermExamDate;

    @Column(nullable = true)
    public LocalTime midtermExamTime;

    public LocalDate finalExamDate;

    @Column(nullable = true)
    public LocalTime finalExamTime;


}