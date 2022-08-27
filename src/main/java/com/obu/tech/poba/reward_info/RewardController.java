package com.obu.tech.poba.reward_info;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.published_info.Published;
import com.obu.tech.poba.published_info.PublishedDto;
import com.obu.tech.poba.published_info.PublishedJoin;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.*;

@Slf4j
@Controller
@RolesAllowed(ROLE_REWARD_ACCESS)
@RequestMapping("/rewards")
public class RewardController {
    static final String FRAGMENT_REWARDS = "reward-info/reward :: reward";
    static final String FRAGMENT_REWARDS_FORM = "reward-info/reward-form :: reward-form";
    static final String FRAGMENT_REWARDS_DETAIL = "reward-info/reward-view :: reward-view";


    @Autowired
    private RewardService rewardService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @Autowired ProfileService profileService;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_REWARD_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<RewardDto>> search(@ModelAttribute RewardDto rewardDto) {

        List<RewardDto> listData =rewardService.findBySearchCriteria(rewardDto);
        List<RewardDto> rewardDtos = new ArrayList<>();
        listData.forEach( d -> {
            RewardDto reward = new RewardDto();
            BeanUtils.copyProperties(d, reward);
            List<RewardDto> rewards = rewardService.findRewardByStaffId(Long.toString(d.getStaffId()));
            BeanUtils.copyProperties(rewards.get(0),reward,"prefix","name");
            rewardDtos.add(reward);
        });
        return ResponseEntity.ok().body(rewardDtos);
    }

    @RolesAllowed(ROLE_REWARD_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        return formAdd(new RewardDto(),request);
    }

    @RolesAllowed({ROLE_REWARD_ADD,ROLE_REWARD_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("rewardDto") @Valid RewardDto rewardDto,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(rewardDto,request), bindingResult);
        }
        try {

            RewardDetail rewardDetail = rewardService.saveRewardDetail(rewardDto);

            rewardDto.setRewardId(rewardDetail.getRewardId());

            Reward reward = rewardService.saveReward(rewardDto);

            return viewSuccess(rewardDto,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_REWARDS);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;
        }
    }

    @RolesAllowed({ROLE_REWARD_SEARCH,ROLE_REWARD_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showRewardInfo(@PathVariable String id,HttpServletRequest request){
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_DETAIL);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        List<RewardDto> reward = rewardService.findRewardByStaffId(id);

        Profile profile = profileService.findByPersNo(reward.get(0).getPersNo());
        reward.get(0).setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        reward.get(0).setName(profile.getName()+" "+profile.getSurname());

        view.addObject("reward",reward.get(0));
        return view;
    }

    @RolesAllowed({ROLE_REWARD_SEARCH,ROLE_REWARD_EDIT})
    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "แก้ไขข้อมูล");

        List<RewardDto> reward  = rewardService.findRewardByStaffId(id);

        Profile profile = profileService.findByPersNo(reward.get(0).getPersNo());
        reward.get(0).setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        reward.get(0).setName(profile.getName()+" "+profile.getSurname());
        view.addObject("reward",reward.get(0));
        return view;
    }

    private ModelAndView formAdd(RewardDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(RewardDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("reward", data);
        return view;
    }

    private ModelAndView viewSuccess(RewardDto data,HttpServletRequest request) {
        return view(data,request)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(RewardDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS_DETAIL);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("reward", data);
        return view;
    }

}
