package com.obu.tech.poba.reward_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Entity
@Table(name = "reward", schema = "poba")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long staffId;
    private long rewardId;
    private String persNo;
    @Transient
    private String prefix;
    @Transient
    private String name;
}
