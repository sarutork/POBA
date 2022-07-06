package com.obu.tech.poba.consultant_info.thesis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
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

    private String persNo;

    @Transient
    private String prefix;
    @Transient
    private String name;

    private String consultantPosition;

    public String studentsId;

    @Transient
    private String studentPrefix;
    @Transient
    private String studentName;
    @Transient
    private String studentsLevel;
    @Transient
    private String course;

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
