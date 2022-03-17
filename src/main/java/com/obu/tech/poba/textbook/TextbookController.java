package com.obu.tech.poba.textbook;

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
@RequestMapping("/textbook")
public class TextbookController {
    static final String FRAGMENT_TEXTBOOK = "textbooks/textbook :: textbook";
    static final String FRAGMENT_TEXTBOOK_FORM = "textbooks/textbook-form :: textbook-form";

    @Autowired TextbookService textbookService;

    @Autowired
    private NameConverterUtils nameConverter;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_TEXTBOOK);}

    @GetMapping("/search")
    public ResponseEntity<List<Textbook>> search(@ModelAttribute Textbook textbook) {
        return ResponseEntity.ok().body(textbookService.findBySearchCriteria(textbook));
    }

    @GetMapping("/add")
    public ModelAndView add() {
        TextbookPhase phase = new TextbookPhase();
        phase.setTextbookPhase(1);

        List<TextbookPhase> phases = new ArrayList<>();
        phases.add(phase);

        TextbookDto textbookDto =  new TextbookDto();
        textbookDto.setPhases(phases);

        return formAdd(textbookDto);
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("textbookDto") @Valid TextbookDto textbookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(textbookDto), bindingResult);
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

            return viewSuccess(textbookDto);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_TEXTBOOK).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @RequestMapping(path = "/addPhase", method = { RequestMethod.POST}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView addPhase(@ModelAttribute TextbookDto textbookDto) {

        List<TextbookPhase> phases = textbookDto.getPhases();

        TextbookPhase phase = new TextbookPhase();
        phase.setTextbookPhase(phases.size()+1);

        phases.add(phase);

        textbookDto.setPhases(phases);

        return formAdd(textbookDto);
    }

    @RequestMapping(path = "/removePhase/{id}", method = { RequestMethod.POST}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView removePhase(@ModelAttribute TextbookDto textbookDto, @PathVariable int id) {

        List<TextbookPhase> phases = textbookDto.getPhases();

        phases.removeIf(p -> (p.getTextbookPhase()==id));

        int i = 0;
        for(TextbookPhase phase : textbookDto.getPhases()){
            phase.setTextbookPhase(++i);
        }

        textbookDto.setPhases(phases);

        return formAdd(textbookDto);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        Textbook textbook = textbookService.findById(id);
        textbook.setName(textbook.getName()+" "+textbook.getSurname());

        List<TextbookPhase> phases = textbookService.findByTextbookId(id);

        TextbookDto textbookDto = new TextbookDto();

        BeanUtils.copyProperties(textbook,textbookDto);
        textbookDto.setPhases(phases);

        return view(textbookDto);
    }

    private ModelAndView formAdd(TextbookDto data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(TextbookDto data) {
        return new ModelAndView(FRAGMENT_TEXTBOOK_FORM).addObject("textbook", data);
    }

    private ModelAndView viewSuccess(TextbookDto data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(TextbookDto data) {
        return new ModelAndView(FRAGMENT_TEXTBOOK_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("textbook", data);
    }


}
