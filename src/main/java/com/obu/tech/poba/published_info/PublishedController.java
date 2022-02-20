package com.obu.tech.poba.published_info;

import com.obu.tech.poba.utils.NameConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/published")
public class PublishedController {

    static final String FRAGMENT_PUBLISHED = "published-info/published";
    static final String FRAGMENT_PUBLISHED_FORM = "published-info/published-form";

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private PublishedService publishedService;

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(PublishedDto publishedDto, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED);
        view.addObject("user", "User Name");
        if (bindingResult.hasErrors()) {
            //TODO: Handle error
            System.out.println("Error: "+bindingResult.getAllErrors());
            return view;
        }
        String[] fullName = nameConverterUtils.fullNameToNameNSurname(publishedDto.getName());
        publishedDto.setName(fullName[0]);
        publishedDto.setSurname(fullName[1]);

        String[] pJoinfullName = nameConverterUtils.fullNameToNameNSurname(publishedDto.getPublishedJoinName());
        publishedDto.setPublishedJoinName(pJoinfullName[0]);
        publishedDto.setPublishedJoinSurname(pJoinfullName[1]);

        Published published = publishedService.savePublished(publishedDto);

        publishedDto.setPublishedId(published.getPublishedId());

        PublishedJoin publishedJoin = publishedService.savePublishedJoin(publishedDto);
        return view;
    }
}
