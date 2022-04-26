package com.obu.tech.poba.authenticate;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity(name="login")
@Table(name = "login", schema = "poba")
public class POBAUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String username,password, role;
}
