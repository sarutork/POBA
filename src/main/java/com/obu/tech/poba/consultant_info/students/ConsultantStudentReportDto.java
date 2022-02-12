package com.obu.tech.poba.consultant_info.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsultantStudentReportDto {
    private String prefix;
    private String prefixOther;
    private String name;
    private String surname;
    private String yearOfStudy;
    private String studentsLevel;
    private String course;
    private String department;
    private String countStudent;
}
