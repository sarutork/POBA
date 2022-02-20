package com.obu.tech.poba.published_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublishedDto {
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
    private long publishedJoinId;
    private String publishedJoinPrefix;
    private String publishedJoinPrefixOther;
    private String publishedJoinName;
    private String publishedJoinSurname;
    private Double publishedFund;
    private int publishedQ1;
    private int publishedQ2;
    private int publishedQ3;
    private int publishedQ4;
}
