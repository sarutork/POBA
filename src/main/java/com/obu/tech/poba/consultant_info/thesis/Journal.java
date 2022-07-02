package com.obu.tech.poba.consultant_info.thesis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "journal_info", schema = "poba")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long journalId;
    private long thesisId;

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

}
