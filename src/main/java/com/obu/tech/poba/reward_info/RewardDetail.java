package com.obu.tech.poba.reward_info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "reward_detail", schema = "poba")
public class RewardDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rewardId;
    private String rewardType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rewardDate;
    private String rewardName;
    private String rewardTopic;
    private String rewardInstitution;
    private String rewardLevel;
}
