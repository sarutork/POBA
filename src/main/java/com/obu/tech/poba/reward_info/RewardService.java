package com.obu.tech.poba.reward_info;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                result.setPrefix( !e[2].toString().equals("อื่นๆ")? e[2].toString() : e[3].toString());
                result.setName(e[4].toString()+" "+e[5].toString());
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
                result.setPersNo(e[2].toString());
                result.setPrefix( !e[3].toString().equals("อื่นๆ")? e[3].toString() : e[4].toString());
                result.setName(e[5].toString()+" "+e[6].toString());
                result.setRewardType(e[7].toString());
                result.setRewardName(e[8].toString());
                result.setRewardLevel(e[9].toString());
                result.setRewardDate(LocalDate.parse(e[10].toString()));
                result.setRewardTopic(e[11].toString());
                result.setRewardInstitution(e[12].toString());

                rewardDtos.add(result);
            }
        }
        return rewardDtos;
    }

    public Reward saveReward(RewardDto rewardDto) {
        Reward reward = new Reward();
        BeanUtils.copyProperties(rewardDto,reward);
        return rewardRepository.saveAndFlush(reward);
    }
    public RewardDetail saveRewardDetail(RewardDto rewardDto) {
        RewardDetail rewardDtl = new RewardDetail();
        BeanUtils.copyProperties(rewardDto,rewardDtl);
        return rewardDetailRepository.saveAndFlush(rewardDtl);
    }
}
