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

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หัวข้อ")
    private String conferenceTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อการประชุม")
    private String conferenceName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงานที่จัด")
    private String conferenceInstitution;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับกิจกรรม")
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
