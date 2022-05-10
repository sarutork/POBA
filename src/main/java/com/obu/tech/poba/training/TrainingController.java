package com.obu.tech.poba.training;

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
@RequestMapping("/training")
public class TrainingController {

    static final String FRAGMENT_TRAINING = "trainings/training :: training";
    static final String FRAGMENT_TRAINING_FORM = "trainings/training-form :: training-form";

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private NameConverterUtils nameConverter;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_TRAINING);}

    @GetMapping("/search")
    public ResponseEntity<List<Training>> search(@ModelAttribute Training training) {
        return ResponseEntity.ok().body(trainingService.findBySearchCriteria(training));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new Training());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("training") @Valid Training training, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(training), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(training.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(training.getName());
                training.setName(fullName[0]);
                training.setSurname(fullName[1]);
            }
            if(!StringUtils.isBlank(training.getName2())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(training.getName2());
                training.setName2(fullName[0]);
                training.setSurname2(fullName[1]);
            }
            if(!StringUtils.isBlank(training.getName3())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(training.getName3());
                training.setName3(fullName[0]);
                training.setSurname3(fullName[1]);
            }

            Training trainingRes = trainingService.save(training);
            trainingRes.setName(trainingRes.getName()+" "+trainingRes.getSurname());
            if(StringUtils.isNotEmpty(trainingRes.getName2())) {
                trainingRes.setName2(trainingRes.getName2() + " " + trainingRes.getSurname2());
            }
            if(StringUtils.isNotEmpty(trainingRes.getName3())){
                trainingRes.setName3(trainingRes.getName3()+" "+trainingRes.getSurname3());
            }

            return viewSuccess(trainingRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_TRAINING).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        Training training = trainingService.findById(id);
        training.setName(training.getName()+" "+training.getSurname());
        if(StringUtils.isNotEmpty(training.getName2())) {
            training.setName2(training.getName2() + " " + training.getSurname2());
        }
        if(StringUtils.isNotEmpty(training.getName3())) {
            training.setName3(training.getName3() + " " + training.getSurname3());
        }
        return view(training);
    }

    private ModelAndView formAdd(Training data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Training data) {
        return new ModelAndView(FRAGMENT_TRAINING_FORM).addObject("training", data);
    }

    private ModelAndView viewSuccess(Training data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Training data) {
        return new ModelAndView(FRAGMENT_TRAINING_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("training", data);
    }
}
