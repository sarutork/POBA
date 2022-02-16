package com.obu.tech.poba.reward_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RewardDto {
    private long staffId;
    private long rewardId;
    private String prefix;
    private String prefixOther;
    private String name;
    private String surname;
    private String rewardType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rewardDate;
    private String rewardName;
    private String rewardTopic;
    private String rewardInstitution;
    private String rewardLevel;
}
