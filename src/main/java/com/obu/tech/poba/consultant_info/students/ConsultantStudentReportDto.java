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
    public String prefix;
    public String prefixOther;
    public String name;
    public String surname;
    private String yearOfStudy;
    private String studentsLevel;
    private String course;
    private String countStudent;
}
