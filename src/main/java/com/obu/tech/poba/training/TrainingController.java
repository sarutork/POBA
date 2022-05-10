package com.obu.tech.poba.training;

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

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ModelAndView add() {
        TrainingPhase phase = new TrainingPhase();
        phase.setTrainingPhase(1);

        List<TrainingPhase> phases = new ArrayList<>();
        phases.add(phase);
        
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setPhases(phases);
        return formAdd(trainingDto);

    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("trainingDto") @Valid TrainingDto trainingDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(trainingDto), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(trainingDto.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(trainingDto.getName());
                trainingDto.setName(fullName[0]);
                trainingDto.setSurname(fullName[1]);
            }
            if(!StringUtils.isBlank(trainingDto.getName2())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(trainingDto.getName2());
                trainingDto.setName2(fullName[0]);
                trainingDto.setSurname2(fullName[1]);
            }
            if(!StringUtils.isBlank(trainingDto.getName3())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(trainingDto.getName3());
                trainingDto.setName3(fullName[0]);
                trainingDto.setSurname3(fullName[1]);
            }

            Training training = new Training();

            BeanUtils.copyProperties(trainingDto,training);

            int sumAmount = 0;
            for(TrainingPhase phase: trainingDto.getPhases()){
                sumAmount += phase.getTrainingAmount();
            }
            training.setTrainingAmountTotal(sumAmount);

            Training trainingRes = trainingService.save(training);

            trainingDto.setName(trainingRes.getName()+" "+trainingRes.getSurname());

            if(StringUtils.isNotEmpty(trainingRes.getName2())) {
                trainingDto.setName2(trainingRes.getName2() + " " + trainingRes.getSurname2());
            }
            if(StringUtils.isNotEmpty(trainingRes.getName3())){
                trainingDto.setName3(trainingRes.getName3()+" "+trainingRes.getSurname3());
            }

            long trainingId = trainingRes.getTrainingId();
            List<TrainingPhase> phases = trainingDto.getPhases();
            phases.forEach(p -> p.setTrainingId(trainingId));

            List<TrainingPhase> trainingPhases = trainingService.savePhase(phases);

            return viewSuccess(trainingDto);
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

        List<TrainingPhase> phases = trainingService.findByTrainingId(id);

        TrainingDto trainingDto = new TrainingDto();

        BeanUtils.copyProperties(training,trainingDto);
        trainingDto.setPhases(phases);

        return view(trainingDto);
    }

    private ModelAndView formAdd(TrainingDto data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView formEdit(TrainingDto data) {
        return form(data).addObject("viewName", "แก้ไข");
    }


    private ModelAndView form(TrainingDto data) {
        return new ModelAndView(FRAGMENT_TRAINING_FORM).addObject("training", data);
    }

    private ModelAndView viewSuccess(TrainingDto data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(TrainingDto data) {
        return new ModelAndView(FRAGMENT_TRAINING_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("training", data);
    }

    @RequestMapping(path = "/addPhase",method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView addPhase(@ModelAttribute("trainingDto") TrainingDto trainingDto) {

        List<TrainingPhase> phases = trainingDto.getPhases();

        TrainingPhase phase = new TrainingPhase();
        phase.setTrainingPhase(phases.size()+1);

        phases.add(phase);

        trainingDto.setPhases(phases);

        return formEdit(trainingDto);
    }

    @RequestMapping(path = "/removePhase/{phase}", method = { RequestMethod.POST}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView removePhase(@ModelAttribute("trainingDto") TrainingDto trainingDto, @PathVariable String phase) {

        List<TrainingPhase> phases = trainingDto.getPhases();

        int  phaseInt = 0;
        if(phase.matches("\\d+")){
            phaseInt = Integer.parseInt(phase);
        }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        int finalPhaseInt = phaseInt;

        TrainingPhase tbPhase = phases.stream()
                .filter(p -> p.getTrainingPhase() == finalPhaseInt)
                .findAny()
                .orElse(null);

        if(tbPhase.getTrainingPhaseId() == 0) {
            phases.removeIf(p -> (p.getTrainingPhase() == finalPhaseInt));
        }else{
            phases = trainingService.removePhase(tbPhase.getTrainingPhaseId(),trainingDto.getTrainingId());
        }

        int i = 0;
        for(TrainingPhase phaseE : trainingDto.getPhases()){
            phaseE.setTrainingPhase(++i);
        }

        trainingDto.setPhases(phases);

        return formEdit(trainingDto);
    }
}
