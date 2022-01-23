package com.obu.tech.poba.personnel_info.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "profile", schema = "poba")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long staffId;
    public String prefix, name, surname, structure2, emailOrg, emailPrivate, tel, mobile;
}
