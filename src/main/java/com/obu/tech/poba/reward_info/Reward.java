package com.obu.tech.poba.reward_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private String prefix;
    private String prefixOther;
    private String name;
    private String surname;
}
