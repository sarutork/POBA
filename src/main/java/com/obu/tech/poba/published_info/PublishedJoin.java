package com.obu.tech.poba.published_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "published_join", schema = "poba")
public class PublishedJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long publishedJoinId;
    private long publishedId;
    private String publishedJoinPrefix;
    private String publishedJoinPrefixOther;
    private String publishedJoinName;
    private String publishedJoinSurname;
    private String publishedJoinPrefix2;
    private String publishedJoinPrefixOther2;
    private String publishedJoinName2;
    private String publishedJoinSurname2;
    private String publishedJoinPrefix3;
    private String publishedJoinPrefixOther3;
    private String publishedJoinName3;
    private String publishedJoinSurname3;
    private boolean otherPeople;
    private Double publishedFund;
    @Column(name = "published_q1")
    private int publishedQ1;
    private String fiscalYearQ1;

    @Column(name = "published_q2")
    private int publishedQ2;
    private String fiscalYearQ2;

    @Column(name = "published_q3")
    private int publishedQ3;
    private String fiscalYearQ3;

    @Column(name = "published_q4")
    private int publishedQ4;
    private String fiscalYearQ4;

}
