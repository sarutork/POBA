package com.obu.tech.poba.consultant_info.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {
    public String studentsId;
    public String studentPrefix;
    public String studentPrefixOther;
    public String studentName;
    public String studentSurname;
    public String admissionStatus;
}
