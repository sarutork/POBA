package com.obu.tech.poba.reward_info;

import com.obu.tech.poba.utils.NameConverterUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/rewards")
public class RewardController {
    static final String FRAGMENT_REWARDS = "reward-info/reward";
    static final String FRAGMENT_REWARDS_FORM = "reward-info/reward-form";
    static final String FRAGMENT_REWARDS_DETAIL = "reward-info/reward-view";


    @Autowired
    private RewardService rewardService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS);
        return view;
    }

    @GetMapping("/search")
    public ResponseEntity<List<RewardDto>> search(@ModelAttribute RewardDto rewardDto) {
        return ResponseEntity.ok().body(rewardService.findBySearchCriteria(rewardDto));
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_FORM);
        view.addObject("user", "user");
        view.addObject("viewName", "เพิ่มข้อมูล");
        RewardDto reward = new RewardDto();
        view.addObject("reward", reward);
        return view;
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(RewardDto rewardDto, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS);
        view.addObject("user", "User Name");
        if (bindingResult.hasErrors()) {
            //TODO: Handle error
            System.out.println("Error: "+bindingResult.getAllErrors());
            return view;
        }
        String[] fullName = nameConverterUtils.fullNameToNameNSurname(rewardDto.getName());
        rewardDto.setName(fullName[0]);
        rewardDto.setSurname(fullName[1]);

        RewardDetail rewardDetail = rewardService.saveRewardDetail(rewardDto);

        rewardDto.setRewardId(rewardDetail.getRewardId());

        Reward reward = rewardService.saveReward(rewardDto);

        return view;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showRewardInfo(@PathVariable String id){
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_DETAIL);
        view.addObject("user", "UserName");
        view.addObject("viewName", "ดูข้อมูล");
        List<RewardDto> reward = rewardService.findRewardByStaffId(id);

        String prefix = StringUtils.isBlank(reward.get(0).getPrefixOther())? reward.get(0).getPrefix(): reward.get(0).getPrefixOther();
        reward.get(0).setPrefix(prefix);
        view.addObject("reward",reward.get(0));
        return view;
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id) {
        System.out.println("Edit study info, id: " + id);
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_FORM);
        view.addObject("user", "UserName");
        view.addObject("viewName", "แก้ไขข้อมูล");
        List<RewardDto> reward  = rewardService.findRewardByStaffId(id);
        view.addObject("reward",reward.get(0));
        return view;
    }
}
