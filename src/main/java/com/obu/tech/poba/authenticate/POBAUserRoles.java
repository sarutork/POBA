package com.obu.tech.poba.authenticate;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity(name="roles")
@Table(name = "roles", schema = "poba")
public class POBAUserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String role;
    private String fnName;
}
