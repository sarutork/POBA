package com.obu.tech.poba.press_info;

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
@RequestMapping("/press")
public class PressController {
    static final String FRAGMENT_PRESS_INFO = "press-info/press :: press";
    static final String FRAGMENT_PRESS_FORM = "press-info/press-form :: press-form";

    @Autowired
    NameConverterUtils nameConverter;

    @Autowired
    PressService pressService;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_PRESS_INFO);}

    @GetMapping("/search")
    public ResponseEntity<List<Press>> search(@ModelAttribute Press press) {
        return ResponseEntity.ok().body(pressService.findBySearchCriteria(press));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new Press());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("press") @Valid Press press, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(press), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(press.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getName());
                press.setName(fullName[0]);
                press.setSurname(fullName[1]);
            }
            if(!StringUtils.isBlank(press.getGuestName1())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getGuestName1());
                press.setGuestName1(fullName[0]);
                press.setGuestSurname1(fullName[1]);
            }
            if(!StringUtils.isBlank(press.getGuestName2())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getGuestName2());
                press.setGuestName2(fullName[0]);
                press.setGuestSurname2(fullName[1]);
            }
            if(!StringUtils.isBlank(press.getGuestName3())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getGuestName3());
                press.setGuestName3(fullName[0]);
                press.setGuestSurname3(fullName[1]);
            }

            Press pressRes = pressService.save(press);
            pressRes.setName(press.getName()+" "+pressRes.getSurname());
            pressRes.setGuestName1(pressRes.getGuestName1()+" "+pressRes.getGuestSurname1());
            pressRes.setGuestName2(pressRes.getGuestName2()+" "+pressRes.getGuestSurname2());
            pressRes.setGuestName3(pressRes.getGuestName3()+" "+pressRes.getGuestSurname3());

            return viewSuccess(pressRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_PRESS_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showPresentingInfo(@PathVariable String id){
        Press press = pressService.findById(id);
        press.setName(press.getName()+" "+press.getSurname());
        press.setGuestName1(press.getGuestName1()+" "+press.getGuestSurname1());
        press.setGuestName2(press.getGuestName2()+" "+press.getGuestSurname2());
        press.setGuestName3(press.getGuestName3()+" "+press.getGuestSurname3());

        return view(press);
    }

    private ModelAndView formAdd(Press data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Press data) {
        return new ModelAndView(FRAGMENT_PRESS_FORM).addObject("press", data);
    }

    private ModelAndView viewSuccess(Press data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }
    private ModelAndView view(Press data) {
        return new ModelAndView(FRAGMENT_PRESS_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("press", data);
    }
}
