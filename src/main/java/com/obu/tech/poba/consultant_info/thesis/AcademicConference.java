package com.obu.tech.poba.consultant_info.thesis;

import com.obu.tech.poba.utils.upload.Upload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "academic_conference", schema = "poba")
public class AcademicConference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long conferenceId;
    private long thesisId;
    private long journalId;

    private String conferenceTopic;

    private String conferenceName;

    private String conferenceInstitution;

    private String conferenceLevel;

    @NotNull(message = "โปรดเลือก วันที่เผยแพร่ข้อมูล เริ่ม")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate conferenceDateFrom;

    @NotNull(message = "โปรดเลือก วันที่เผยแพร่ข้อมูล ถึง")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate conferenceDateTo;

    private String conferenceDoc;

    @Transient
    private List<Upload> uploads;

    @Transient
    @ToString.Exclude
    private Long[] filesToKeep;

    @Transient
    @ToString.Exclude
    private MultipartFile[] newFiles;
}
