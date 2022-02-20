package com.obu.tech.poba.published_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "published_info", schema = "poba")
public class Published {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long publishedId;
    private String prefix;
    private String prefixOther;
    private String name;
    private String surname;
    private String publishedStatus;
    private String publishedTopic;
    private String publishedJournal;
    private String publishedYear;
    private String publishedIssue;
    private String publishedMonth;
    private String publishedYear2;
    private String publishedBase;
    private String publishedLevel;
}
