package com.obu.tech.poba.reward_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RewardDto {
    private long staffId;
    private long rewardId;

    @Size(min = 1, max = 255, message = "โปรดเลือก คำนำหน้า")
    private String prefix;
    private String prefixOther;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อ-นามสกุล")
    private String name;
    private String surname;

    @Size(min = 1, max = 255, message = "โปรดเลือก ประเภทรางวัล")
    private String rewardType;

    @NotNull(message = "โปรดเลือก วันที่รับรางวัล")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rewardDate;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อรางวัล")
    private String rewardName;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล ชื่อหัวข้อเรื่อง/บทความ/รางวัล")
    private String rewardTopic;

    @Size(min = 1, max = 255, message = "กรุณาตรวจสอบข้อมูล หน่วยงานที่ให้รางวัล")
    private String rewardInstitution;

    @Size(min = 1, max = 255, message = "โปรดเลือก ระดับกิจกรรม")
    private String rewardLevel;
}
