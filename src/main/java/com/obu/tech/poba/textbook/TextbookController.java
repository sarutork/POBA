package com.obu.tech.poba.textbook;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.*;

@Slf4j
@Controller
@RolesAllowed(ROLE_TEXTBOOK_ACCESS)
@RequestMapping("/textbook")
public class TextbookController {
    static final String FRAGMENT_TEXTBOOK = "textbooks/textbook :: textbook";
    static final String FRAGMENT_TEXTBOOK_FORM = "textbooks/textbook-form :: textbook-form";

    @Autowired TextbookService textbookService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_TEXTBOOK);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_TEXTBOOK_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Textbook>> search(@ModelAttribute Textbook textbook) {
        return ResponseEntity.ok().body(textbookService.findBySearchCriteria(textbook));
    }

    @RolesAllowed(ROLE_TEXTBOOK_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        TextbookPhase phase = new TextbookPhase();
        phase.setTextbookPhase(1);

        List<TextbookPhase> phases = new ArrayList<>();
        phases.add(phase);

        TextbookDto textbookDto =  new TextbookDto();
        textbookDto.setPhases(phases);

        return formAdd(textbookDto,request);
    }

    @RolesAllowed({ROLE_TEXTBOOK_ADD,ROLE_TEXTBOOK_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("textbookDto") @Valid TextbookDto textbookDto,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(textbookDto,request), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(textbookDto.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(textbookDto.getName());
                textbookDto.setName(fullName[0]);
                textbookDto.setSurname(fullName[1]);
            }

            Textbook textbook = new Textbook();

            BeanUtils.copyProperties(textbookDto,textbook);

            int sumAmount = 0;
            for(TextbookPhase phase: textbookDto.getPhases()){
                sumAmount += phase.getTextbookAmount();
            }
            textbook.setTextbookAmountTotal(sumAmount);

            Textbook textbookRes = textbookService.save(textbook);
            textbookDto.setName(textbookRes.getName()+" "+textbookRes.getSurname());

            long textbookId = textbookRes.getTextbookId();
            List<TextbookPhase> phases = textbookDto.getPhases();
            phases.forEach(p -> p.setTextbookId(textbookId));

            List<TextbookPhase> textbookPhaseRes = textbookService.saveTextbookPhase(phases);

            return viewSuccess(textbookDto,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_TEXTBOOK);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_TEXTBOOK_ADD,ROLE_TEXTBOOK_EDIT})
    @RequestMapping(path = "/addPhase",method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView addPhase(@ModelAttribute("textbookDto") TextbookDto textbookDto,HttpServletRequest request) {

        List<TextbookPhase> phases = textbookDto.getPhases();

        TextbookPhase phase = new TextbookPhase();
        phase.setTextbookPhase(phases.size()+1);

        phases.add(phase);

        textbookDto.setPhases(phases);

        return formEdit(textbookDto,request);
    }

    @RolesAllowed({ROLE_TEXTBOOK_EDIT,ROLE_TEXTBOOK_ADD})
    @RequestMapping(path = "/removePhase/{phase}", method = { RequestMethod.POST}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView removePhase(@ModelAttribute("textbookDto") TextbookDto textbookDto,
                                    @PathVariable String phase,HttpServletRequest request) {

        List<TextbookPhase> phases = textbookDto.getPhases();

        int  phaseInt = 0;
        if(phase.matches("\\d+")){
            phaseInt = Integer.parseInt(phase);
        }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        int finalPhaseInt = phaseInt;

        TextbookPhase tbPhase = phases.stream()
                .filter(p -> p.getTextbookPhase() == finalPhaseInt)
                .findAny()
                .orElse(null);

        if(tbPhase.getTextbookPhaseId() == 0) {
            phases.removeIf(p -> (p.getTextbookPhase() == finalPhaseInt));
        }else{
            phases = textbookService.removePhase(tbPhase.getTextbookPhaseId(),textbookDto.getTextbookId());
        }

        int i = 0;
        for(TextbookPhase phaseE : textbookDto.getPhases()){
            phaseE.setTextbookPhase(++i);
        }

        textbookDto.setPhases(phases);

        return formEdit(textbookDto,request);
    }

    @RolesAllowed({ROLE_TEXTBOOK_SEARCH,ROLE_TEXTBOOK_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        Textbook textbook = textbookService.findById(id);
        textbook.setName(textbook.getName()+" "+textbook.getSurname());

        List<TextbookPhase> phases = textbookService.findByTextbookId(id);

        TextbookDto textbookDto = new TextbookDto();

        BeanUtils.copyProperties(textbook,textbookDto);
        textbookDto.setPhases(phases);

        return view(textbookDto,request);
    }

    private ModelAndView formAdd(TextbookDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView formEdit(TextbookDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "แก้ไข");
    }

    private ModelAndView form(TextbookDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_TEXTBOOK_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("textbook", data);
        return view;

    }

    private ModelAndView viewSuccess(TextbookDto data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(TextbookDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_TEXTBOOK_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("textbook", data);
        return view;
    }


}
