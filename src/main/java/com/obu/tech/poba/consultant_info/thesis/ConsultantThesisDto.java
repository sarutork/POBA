package com.obu.tech.poba.consultant_info.thesis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@Setter
public class ConsultantThesisDto {
    private long thesisId;
    private String persNo;
    private String prefix;
    private String name;
    private String consultantPosition;
    public String studentsId;
    private String studentPrefix;
    private String studentName;
    private String studentsLevel;
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
    private long journalId;
    private String publishedTopic;
    private String publishedJournal;
    private String publishedYear;
    private String publishedIssue;
    private String publishedPage;
    private String publishedMonth;
    private String monthOther;
    private String publishedYear2;
    private String publishedBase;
    private String publishedLevel;
    private long conferenceId;
    private String conferenceTopic;
    private String conferenceName;
    private String conferenceInstitution;
    private String conferenceLevel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate conferenceDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate conferenceDateTo;

}
