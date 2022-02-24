package com.obu.tech.poba.published_info;

import com.obu.tech.poba.utils.NameConverterUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/published")
public class PublishedController {

    static final String FRAGMENT_PUBLISHED = "published-info/published";
    static final String FRAGMENT_PUBLISHED_FORM = "published-info/published-form";

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private PublishedService publishedService;

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED);
        return view;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Published>> search(@ModelAttribute Published published) {
        return ResponseEntity.ok().body(publishedService.findBySearchCriteria(published));
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "ดูข้อมูล");

        Published published = publishedService.findPublishedById(id);
        List<PublishedJoin> publishedJoin = publishedService.findPublishedJoinById(published.getPublishedId());

        PublishedDto publishedDto = new PublishedDto();
        BeanUtils.copyProperties(published,publishedDto);
        BeanUtils.copyProperties(publishedJoin.get(0),publishedDto,"prefix","prefixOther","name","surname");
        publishedDto.setPublishedJoinPrefix(publishedJoin.get(0).getPrefix());
        publishedDto.setPublishedJoinPrefixOther(publishedJoin.get(0).getPrefixOther());
        publishedDto.setPublishedJoinName(publishedJoin.get(0).getName());
        publishedDto.setPublishedJoinSurname(publishedJoin.get(0).getSurname());

        view.addObject("published",publishedDto);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED_FORM);
        view.addObject("user", "UserName");
        view.addObject("viewName", "เพิ่มข้อมูล");
        PublishedDto publishedDto = new PublishedDto();
        view.addObject("published", publishedDto);
        return view;
    }

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
