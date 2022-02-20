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
    private String prefix;
    private String prefixOther;
    private String name;
    private String surname;
    private Double publishedFund;
    private int publishedQ1;
    private int publishedQ2;
    private int publishedQ3;
    private int publishedQ4;
}
