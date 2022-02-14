package com.obu.tech.poba.reward_info;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rewards")
public class RewardController {
    static final String FRAGMENT_REWARDS = "reward-info/reward :: rewards";

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(FRAGMENT_REWARDS);
        return view;
    }
}
