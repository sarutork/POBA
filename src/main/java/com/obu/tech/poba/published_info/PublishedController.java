package com.obu.tech.poba.published_info;

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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/published")
public class PublishedController {

    static final String FRAGMENT_PUBLISHED = "published-info/published :: published";
    static final String FRAGMENT_PUBLISHED_FORM = "published-info/published-form :: published-form";

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private PublishedService publishedService;

    @GetMapping
    public ModelAndView showListView() {
      return new ModelAndView(FRAGMENT_PUBLISHED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PublishedDto>> search(@ModelAttribute Published published) {
            List<PublishedDto> publishedDtoList = new ArrayList<>();
            List<Published> publishedList = publishedService.findBySearchCriteria(published);
            publishedList.forEach( p -> {
                List<PublishedJoin> publishedJoin = publishedService.findPublishedJoinById(p.getPublishedId());
                PublishedDto publishedDto = new PublishedDto();
                if(publishedJoin.size() > 0) {
                    BeanUtils.copyProperties(p, publishedDto);
                    BeanUtils.copyProperties(publishedJoin.get(0), publishedDto, "prefix", "prefixOther", "name", "surname");
                    publishedDto.setPublishedJoinPrefix(publishedJoin.get(0).getPrefix());
                    publishedDto.setPublishedJoinPrefixOther(publishedJoin.get(0).getPrefixOther());
                    publishedDto.setPublishedJoinName(publishedJoin.get(0).getName());
                    publishedDto.setPublishedJoinSurname(publishedJoin.get(0).getSurname());
                }
                publishedDtoList.add(publishedDto);
            });

        return ResponseEntity.ok().body(publishedDtoList);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED_FORM);
        view.addObject("viewName", "ดูข้อมูล");

        Published published = publishedService.findPublishedById(id);
        List<PublishedJoin> publishedJoin = publishedService.findPublishedJoinById(published.getPublishedId());

        PublishedDto publishedDto = new PublishedDto();
        BeanUtils.copyProperties(published,publishedDto);
        BeanUtils.copyProperties(publishedJoin.get(0),publishedDto,"prefix","prefixOther","name","surname");
        publishedDto.setPublishedJoinPrefix(publishedJoin.get(0).getPrefix());
        publishedDto.setPublishedJoinPrefixOther(publishedJoin.get(0).getPrefixOther());
        publishedDto.setPublishedJoinName(publishedJoin.get(0).getName() +" "+publishedJoin.get(0).getSurname());
        publishedDto.setPublishedJoinSurname(publishedJoin.get(0).getSurname());

        publishedDto.setName(published.getName() +" "+published.getSurname());

        view.addObject("published",publishedDto);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return formAdd(new PublishedDto());
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("publishedDto") @Valid PublishedDto publishedDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(publishedDto), bindingResult);
        }
        try {
            if(!StringUtils.isBlank(publishedDto.getName())) {
                String[] fullName = nameConverterUtils.fullNameToNameNSurname(publishedDto.getName());
                publishedDto.setName(fullName[0]);
                publishedDto.setSurname(fullName[1]);
            }

            if(!StringUtils.isBlank(publishedDto.getPublishedJoinName())) {
                String[] pJoinfullName = nameConverterUtils.fullNameToNameNSurname(publishedDto.getPublishedJoinName());
                publishedDto.setPublishedJoinName(pJoinfullName[0]);
                publishedDto.setPublishedJoinSurname(pJoinfullName[1]);
            }

            Published published = publishedService.savePublished(publishedDto);

            publishedDto.setPublishedId(published.getPublishedId());

            PublishedJoin publishedJoin = publishedService.savePublishedJoin(publishedDto);

            publishedDto.setName(publishedDto.getName()+" "+publishedDto.getSurname());
            publishedDto.setPublishedJoinName(publishedDto.getPublishedJoinName()+" "+publishedDto.getPublishedJoinSurname());

            return viewSuccess(publishedDto);

        }catch (Exception e){
        e.printStackTrace();
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return new ModelAndView(FRAGMENT_PUBLISHED).addObject("responseMessage", "ไม่สำเร็จ");
    }
    }

    private ModelAndView formAdd(PublishedDto data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(PublishedDto data) {
        return new ModelAndView(FRAGMENT_PUBLISHED_FORM).addObject("published", data);
    }

    private ModelAndView viewSuccess(PublishedDto data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(PublishedDto data) {
        return new ModelAndView(FRAGMENT_PUBLISHED_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("published", data);
    }
}
