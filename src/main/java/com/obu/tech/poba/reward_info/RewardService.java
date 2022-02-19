package com.obu.tech.poba.reward_info;

import com.obu.tech.poba.consultant_info.students.ConsultantDto;
import com.obu.tech.poba.teaching_info.Teaching;
import com.obu.tech.poba.teaching_info.TeachingRepository;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class RewardService {
    @Autowired
    RewardRepository rewardRepository;
    @Autowired
    RewardDetailRepository rewardDetailRepository;

    public List<RewardDto> findBySearchCriteria(RewardDto rewardDto){
        List<Object[]> rewardList = rewardRepository.findReward(
                "%"+rewardDto.getName()+"%",
                rewardDto.getRewardLevel(),
                rewardDto.getRewardDate());

        List<RewardDto> rewardDtos = new ArrayList<>();
        if (!rewardList.isEmpty() && rewardList.size() >0){
            for(final Object[] e : rewardList){
                final RewardDto result = new RewardDto();
                result.setStaffId(Long.parseLong(e[0].toString()));
                result.setRewardId(Long.parseLong(e[1].toString()));
                result.setPrefix(e[2].toString());
                result.setPrefixOther(e[3].toString());
                result.setName(e[4].toString());
                result.setSurname(e[5].toString());
                result.setRewardType(e[6].toString());
                result.setRewardName(e[7].toString());
                result.setRewardLevel(e[8].toString());
                result.setRewardDate(LocalDate.parse(e[9].toString()));
                rewardDtos.add(result);
            }
        }
        return rewardDtos;
    }

    public List<RewardDto>  findRewardByStaffId(String  id){
        List<Object[]> rewardList= rewardRepository.findRewardByStaffId(Long.parseLong(id));

        List<RewardDto> rewardDtos = new ArrayList<>();
        if (!rewardList.isEmpty() && rewardList.size() >0){
            for(final Object[] e : rewardList){
                final RewardDto result = new RewardDto();
                result.setStaffId(Long.parseLong(e[0].toString()));
                result.setRewardId(Long.parseLong(e[1].toString()));
                result.setPrefix(e[2].toString());
                result.setPrefixOther(e[3].toString());
                result.setName(e[4].toString());
                result.setSurname(e[5].toString());
                result.setRewardType(e[6].toString());
                result.setRewardName(e[7].toString());
                result.setRewardLevel(e[8].toString());
                result.setRewardDate(LocalDate.parse(e[9].toString()));
                result.setRewardTopic(e[10].toString());
                result.setRewardInstitution(e[11].toString());

                rewardDtos.add(result);
            }
        }
        return rewardDtos;
    }

    public Reward saveReward(RewardDto rewardDto) {

        Reward reward = new Reward();
        reward.setStaffId(rewardDto.getStaffId());
        reward.setRewardId(rewardDto.getRewardId());
        reward.setPrefix(rewardDto.getPrefix());
        reward.setPrefixOther(rewardDto.getPrefixOther());
        reward.setName(rewardDto.getName());
        reward.setSurname(rewardDto.getSurname());

        return rewardRepository.saveAndFlush(reward);
    }
    public RewardDetail saveRewardDetail(RewardDto rewardDto) {
        RewardDetail rewardDtl = new RewardDetail();
        rewardDtl.setRewardId(rewardDto.getRewardId());
        rewardDtl.setRewardType(rewardDto.getRewardType());
        rewardDtl.setRewardDate(rewardDto.getRewardDate());
        rewardDtl.setRewardName(rewardDto.getRewardName());
        rewardDtl.setRewardTopic(rewardDto.getRewardTopic());
        rewardDtl.setRewardInstitution(rewardDto.getRewardInstitution());
        rewardDtl.setRewardLevel(rewardDto.getRewardLevel());

        return rewardDetailRepository.saveAndFlush(rewardDtl);
    }
}
