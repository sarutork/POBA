package com.obu.tech.poba.external_academic_service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "external_academic_services", schema = "poba")
public class ExternalAcServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "โปรดเลือก คำนำหน้า และ ชื่อ-นามสกุล ")
    private String persNo;
    @Transient
    private String prefix;
    @Transient
    private String name;
    private String title;
    private String type;
    private String typeOther;
    private String level;
    private String institution;
    private String year;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private String startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String endTime;
    private String location;
    private int numOfParticipants;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate letterReceivedDate;
    private String letterNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate letterSentDate;
    private String letterSendingNo;
    private String coordinator;
    private String coordinatorTel;
    private String confirmationNo;
    private String note;
}
