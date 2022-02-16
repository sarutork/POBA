package com.obu.tech.poba.reward_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long>, JpaSpecificationExecutor<Reward> {
    @Query("SELECT r.staffId,r.rewardId,r.prefix,r.prefixOther,r.name, r.surname, rd.rewardType, rd.rewardName, rd.rewardLevel, rd.rewardDate" +
            " FROM Reward r" +
            " JOIN RewardDetail rd on r.rewardId = rd.rewardId" +
            " WHERE (:name is null or :name = '' or r.name LIKE :name" +
            " or r.surname LIKE :name)" +
            " and (:rewardLevel is null or :rewardLevel = ''  or rd.rewardLevel = :rewardLevel)" +
            " and (:rewardDate is null or :rewardDate = '' or rd.rewardDate = :rewardDate)")
    List<Object[]> findReward(@Param("name") String name,
                                         @Param("rewardLevel") String rewardLevel,
                                         @Param("rewardDate") String rewardDate);

    @Query("SELECT r.staffId,r.rewardId,r.prefix,r.prefixOther,r.name, r.surname, rd.rewardType, rd.rewardName, rd.rewardLevel, rd.rewardDate" +
            ", rd.rewardTopic, rd.rewardInstitution" +
            " FROM Reward r" +
            " JOIN RewardDetail rd on r.rewardId = rd.rewardId" +
            " WHERE r.staffId = :staffId")
    List<Object[]> findRewardByStaffId(@Param("staffId") Long staffId);
}
