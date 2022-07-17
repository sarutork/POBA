package com.obu.tech.poba.reward_info;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
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

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
        return ResponseEntity.ok().body(rewardService.findBySearchCriteria(rewardDto));
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
            if(!StringUtils.isBlank(rewardDto.getName())) {
                String[] fullName = nameConverterUtils.fullNameToNameNSurname(rewardDto.getName());
                rewardDto.setName(fullName[0]);
                rewardDto.setSurname(fullName[1]);
            }

            RewardDetail rewardDetail = rewardService.saveRewardDetail(rewardDto);

            rewardDto.setRewardId(rewardDetail.getRewardId());

            Reward reward = rewardService.saveReward(rewardDto);

            rewardDto.setName(rewardDto.getName()+" "+rewardDto.getSurname());

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

        String prefix = StringUtils.isBlank(reward.get(0).getPrefixOther())? reward.get(0).getPrefix(): reward.get(0).getPrefixOther();
        reward.get(0).setPrefix(prefix);

        reward.get(0).setName(reward.get(0).getName()+" "+reward.get(0).getSurname());

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
        reward.get(0).setName(reward.get(0).getName()+" "+reward.get(0).getSurname());
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
