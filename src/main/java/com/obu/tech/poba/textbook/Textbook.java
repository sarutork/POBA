package com.obu.tech.poba.textbook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "textbook", schema = "poba")
public class Textbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long textbookId;

    private String persNo;

    @Transient
    private String prefix;
    @Transient
    private String name;

    private String textbookType;

    private String textbookAnnounce;
    private String textbookContract;

    private String textbookTopic;

    private String textbookFund;

    private double textbookAmountTotal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookDateTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate textbookExtendDate3;

    private String textbookStatus;

    private String textbookPbType;

    private String textbookIssue;

    private String textbookYear;

    private String textbookInstitution;

    private String textbookDiffText;

    private String textbookRef;

    @Column(name = "textbook_tci")
    private String textbookTCI;
    private String textbookLevel;
}
