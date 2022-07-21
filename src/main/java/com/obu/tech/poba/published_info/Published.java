package com.obu.tech.poba.published_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Entity
@Table(name = "published_info", schema = "poba")
public class Published {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long publishedId;
    private String persNo;
    @Transient
    private String prefix;
    @Transient
    private String name;
    private String fullNamePublisher;
    private String fullNameJoiner;
    private String publishedStatus;
    private String publishedStatusOther;
    private String publishedType;
    private String publishedTypeOther;
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
