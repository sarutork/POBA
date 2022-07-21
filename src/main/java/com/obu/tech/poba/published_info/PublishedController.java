package com.obu.tech.poba.published_info;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.YearGeneratorUtils;
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
@RolesAllowed(ROLE_PUBLISHED_ACCESS)
@RequestMapping("/published")
public class PublishedController {

    static final String FRAGMENT_PUBLISHED = "published-info/published :: published";
    static final String FRAGMENT_PUBLISHED_FORM = "published-info/published-form :: published-form";

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private PublishedService publishedService;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_PUBLISHED_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<PublishedDto>> search(@ModelAttribute Published published) {
            List<PublishedDto> publishedDtoList = new ArrayList<>();
            List<Published> publishedList = publishedService.findBySearchCriteria(published);
            publishedList.forEach( p -> {
                List<PublishedJoin> publishedJoin = publishedService.findPublishedJoinById(p.getPublishedId());
                PublishedDto publishedDto = new PublishedDto();
                if(publishedJoin.size() > 0) {
                    BeanUtils.copyProperties(p, publishedDto);
                    BeanUtils.copyProperties(publishedJoin.get(0), publishedDto);
                }
                publishedDtoList.add(publishedDto);
            });

        return ResponseEntity.ok().body(publishedDtoList);
    }

    @RolesAllowed({ROLE_PUBLISHED_SEARCH,ROLE_PUBLISHED_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){

        Published published = publishedService.findPublishedById(id);
        List<PublishedJoin> publishedJoin = publishedService.findPublishedJoinById(published.getPublishedId());

        PublishedDto publishedDto = new PublishedDto();

        BeanUtils.copyProperties(published,publishedDto);
        BeanUtils.copyProperties(publishedJoin.get(0),publishedDto);

        if(StringUtils.isNotEmpty(publishedJoin.get(0).getPublishedJoinName())){
            publishedDto.setPublishedJoinName(publishedJoin.get(0).getPublishedJoinName() + " " + publishedJoin.get(0).getPublishedJoinSurname());
        }

        if(StringUtils.isNotEmpty(publishedJoin.get(0).getPublishedJoinName2())){
            publishedDto.setPublishedJoinName2(publishedJoin.get(0).getPublishedJoinName2() + " " + publishedJoin.get(0).getPublishedJoinSurname2());
        }

        if(StringUtils.isNotEmpty(publishedJoin.get(0).getPublishedJoinName3())){
            publishedDto.setPublishedJoinName3(publishedJoin.get(0).getPublishedJoinName3() + " " + publishedJoin.get(0).getPublishedJoinSurname3());
        }

        Profile profile = profileService.findByPersNo(published.getPersNo());
        publishedDto.setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        publishedDto.setName(profile.getName()+" "+profile.getSurname());

        List<FiscalYear> fiscalYears = publishedService.findFiscalYearByPublishedId(published.getPublishedId());
        publishedDto.setFiscalYears(fiscalYears);

        return view(publishedDto,request);
    }

    @RolesAllowed(ROLE_PUBLISHED_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        return formAdd(new PublishedDto(),request);
    }

    @RolesAllowed({ROLE_PUBLISHED_ADD,ROLE_PUBLISHED_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("publishedDto") @Valid PublishedDto publishedDto,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(publishedDto,request), bindingResult);
        }
        try {

            if(!StringUtils.isBlank(publishedDto.getPublishedJoinName())) {
                String[] pJoinfullName = nameConverterUtils.fullNameToNameNSurname(publishedDto.getPublishedJoinName());
                publishedDto.setPublishedJoinName(pJoinfullName[0]);
                publishedDto.setPublishedJoinSurname(pJoinfullName[1]);
            }

            if(!StringUtils.isBlank(publishedDto.getPublishedJoinName2())) {
                String[] pJoinfullName2 = nameConverterUtils.fullNameToNameNSurname(publishedDto.getPublishedJoinName2());
                publishedDto.setPublishedJoinName2(pJoinfullName2[0]);
                publishedDto.setPublishedJoinSurname2(pJoinfullName2[1]);
            }

            if(!StringUtils.isBlank(publishedDto.getPublishedJoinName3())) {
                String[] pJoinfullName3 = nameConverterUtils.fullNameToNameNSurname(publishedDto.getPublishedJoinName3());
                publishedDto.setPublishedJoinName3(pJoinfullName3[0]);
                publishedDto.setPublishedJoinSurname3(pJoinfullName3[1]);
            }

            Published published = publishedService.savePublished(publishedDto);

            publishedDto.setPublishedId(published.getPublishedId());

            PublishedJoin publishedJoin = publishedService.savePublishedJoin(publishedDto);

            publishedDto.setPublishedJoinName(publishedDto.getPublishedJoinName()+" "+publishedDto.getPublishedJoinSurname());

            if(publishedDto.getFiscalYears() != null && publishedDto.getFiscalYears().size() !=0) {
                long publishedId = published.getPublishedId();
                List<FiscalYear> fiscalYears = publishedDto.getFiscalYears();

                fiscalYears.removeIf(p -> (StringUtils.isBlank(p.getYear())));

                fiscalYears.forEach(p -> p.setPublishedId(publishedId));

                publishedService.removeByPublishedId(publishedId);
                publishedService.saveFiscalYear(fiscalYears);
            }

            return viewSuccess(publishedDto,request);

        }catch (Exception e){
        e.printStackTrace();
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
        return view;

    }
    }

    private ModelAndView formAdd(PublishedDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(PublishedDto data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        view.addObject("published", data);
        return view;
    }

    private ModelAndView viewSuccess(PublishedDto data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(PublishedDto data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_PUBLISHED_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("years", years);
        view.addObject("published", data);
        return view;

    }
}
