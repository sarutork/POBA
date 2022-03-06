package com.obu.tech.poba.reward_info;

import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/rewards")
public class RewardController {
    static final String FRAGMENT_REWARDS = "reward-info/reward :: reward";
    static final String FRAGMENT_REWARDS_FORM = "reward-info/reward-form :: reward-form";
    static final String FRAGMENT_REWARDS_DETAIL = "reward-info/reward-view :: reward-view";


    @Autowired
    private RewardService rewardService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_REWARDS);}

    @GetMapping("/search")
    public ResponseEntity<List<RewardDto>> search(@ModelAttribute RewardDto rewardDto) {
        return ResponseEntity.ok().body(rewardService.findBySearchCriteria(rewardDto));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new RewardDto());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("rewardDto") @Valid RewardDto rewardDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(rewardDto), bindingResult);
        }
        try {
            if(!StringUtils.isBlank(rewardDto.getName())) {
                String[] fullName = nameConverterUtils.fullNameToNameNSurname(rewardDto.getName());
                rewardDto.setName(fullName[0]);
                rewardDto.setSurname(fullName[1]);
            }

            RewardDetail rewardDetail = rewardService.saveRewardDetail(rewardDto);

            rewardDto.setRewardId(rewardDetail.getRewardId());

            Reward reward = rewardService.saveReward(rewardDto);

            rewardDto.setName(rewardDto.getName()+" "+rewardDto.getSurname());

            return viewSuccess(rewardDto);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_REWARDS).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showRewardInfo(@PathVariable String id){
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_DETAIL);
        view.addObject("viewName", "ดูข้อมูล");
        List<RewardDto> reward = rewardService.findRewardByStaffId(id);

        String prefix = StringUtils.isBlank(reward.get(0).getPrefixOther())? reward.get(0).getPrefix(): reward.get(0).getPrefixOther();
        reward.get(0).setPrefix(prefix);

        reward.get(0).setName(reward.get(0).getName()+" "+reward.get(0).getSurname());

        view.addObject("reward",reward.get(0));
        return view;
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id) {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_FORM);
        view.addObject("viewName", "แก้ไขข้อมูล");
        List<RewardDto> reward  = rewardService.findRewardByStaffId(id);
        reward.get(0).setName(reward.get(0).getName()+" "+reward.get(0).getSurname());
        view.addObject("reward",reward.get(0));
        return view;
    }

    private ModelAndView formAdd(RewardDto data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(RewardDto data) {
        return new ModelAndView(FRAGMENT_REWARDS_FORM).addObject("reward", data);
    }

    private ModelAndView viewSuccess(RewardDto data) {
        return view(data)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(RewardDto data) {
        return new ModelAndView(FRAGMENT_REWARDS_DETAIL).addObject("reward", data);
    }

}
