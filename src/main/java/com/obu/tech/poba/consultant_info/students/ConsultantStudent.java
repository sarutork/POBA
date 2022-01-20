package com.obu.tech.poba.consultant_info.students;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "consultant_students", schema = "poba")
public class ConsultantStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;
    public String prefix;
    public String name;
    public String surname;
    public int studentsId;
    public String studentPrefix;
    public String yearOfStudy;
    public String admissionStatus;
    public String studentsLevel;
    public String course;
    public String department;
}
