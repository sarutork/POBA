package com.obu.tech.poba.project;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "participant", schema = "poba")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long projectId;
    private String participantId;
    private String name;
    private String status;

}
