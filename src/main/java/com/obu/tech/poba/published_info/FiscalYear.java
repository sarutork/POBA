package com.obu.tech.poba.published_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "fiscal_year", schema = "poba")
public class FiscalYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long publishedId;
    private String year;
    private String quarter;
    private int countRef;
}
