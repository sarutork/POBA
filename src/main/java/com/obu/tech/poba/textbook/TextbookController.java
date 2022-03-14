package com.obu.tech.poba.textbook;

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
    public ModelAndView add() {return formAdd(new Textbook());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("textbook") @Valid Textbook textbook, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(textbook), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(textbook.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(textbook.getName());
                textbook.setName(fullName[0]);
                textbook.setSurname(fullName[1]);
            }

            Textbook textbookRes = textbookService.save(textbook);
            textbookRes.setName(textbookRes.getName()+" "+textbookRes.getSurname());

            return viewSuccess(textbookRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_TEXTBOOK).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showPresentingInfo(@PathVariable String id){
        Textbook textbook = textbookService.findById(id);
        textbook.setName(textbook.getName()+" "+textbook.getSurname());
        return view(textbook);
    }

    private ModelAndView formAdd(Textbook data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Textbook data) {
        return new ModelAndView(FRAGMENT_TEXTBOOK_FORM).addObject("textbook", data);
    }

    private ModelAndView viewSuccess(Textbook data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Textbook data) {
        return new ModelAndView(FRAGMENT_TEXTBOOK_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("textbook", data);
    }


}
