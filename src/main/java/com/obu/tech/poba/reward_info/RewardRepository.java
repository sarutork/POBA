package com.obu.tech.poba.reward_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long>, JpaSpecificationExecutor<Reward> {
    @Query("SELECT r.staffId,r.rewardId,p.prefix,p.prefixOther,p.name, p.surname, rd.rewardType, rd.rewardName, rd.rewardLevel, rd.rewardDate" +
            " FROM Reward r" +
            " JOIN RewardDetail rd on r.rewardId = rd.rewardId" +
            " JOIN Profile p ON p.persNo = r.persNo" +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:rewardLevel is null or :rewardLevel = ''  or rd.rewardLevel = :rewardLevel)" +
            " and (:rewardDate is null or rd.rewardDate = :rewardDate)")
    List<Object[]> findReward(@Param("name") String name,
                                         @Param("rewardLevel") String rewardLevel,
                                         @Param("rewardDate") LocalDate rewardDate);

    @Query("SELECT r.staffId,r.rewardId,p.persNo,p.prefix,p.prefixOther,p.name, p.surname, rd.rewardType, rd.rewardName, rd.rewardLevel, rd.rewardDate" +
            ", rd.rewardTopic, rd.rewardInstitution" +
            " FROM Reward r" +
            " JOIN RewardDetail rd on r.rewardId = rd.rewardId" +
            " JOIN Profile p ON p.persNo = r.persNo" +
            " WHERE r.staffId = :staffId")
    List<Object[]> findRewardByStaffId(@Param("staffId") Long staffId);
}
