package com.obu.tech.poba.consultant_info.thesis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "consultant_thesis", schema = "poba")
public class ConsultantThesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long thesisId;

    private String prefix;
    private String prefixOther;

    private String name;
    private String surname;

    private String consultantPosition;

    public String studentsId;

    private String studentPrefix;
    private String studentPrefixOther;

    private String studentName;
    private String studentSurname;

    private String studentLevel;

    private String courseName;

    private String thesisType;
    private String thesisTopic;
    private String thesisConsider;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisStartdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisEnddate;
    private String thesisApprove;
    private String thesisSuccess;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thesisSuccessDate;
    private String thesisAssessment;
}
