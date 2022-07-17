package com.obu.tech.poba.training;

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
@RolesAllowed(ROLE_TRAINING_ACCESS)
@RequestMapping("/training")
public class TrainingController {

    static final String FRAGMENT_TRAINING = "trainings/training :: training";
    static final String FRAGMENT_TRAINING_FORM = "trainings/training-form :: training-form";

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_TRAINING);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_TRAINING_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Training>> search(@ModelAttribute Training training) {
        return ResponseEntity.ok().body(trainingService.findBySearchCriteria(training));
    }

    @RolesAllowed(ROLE_TRAINING_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        TrainingPhase phase = new TrainingPhase();
        phase.setTrainingPhase(1);

        List<TrainingPhase> phases = new ArrayList<>();
        phases.add(phase);
        
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setPhases(phases);
        return formAdd(trainingDto,request);

    }

    @RolesAllowed({ROLE_TRAINING_EDIT,ROLE_TRAINING_ADD})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("trainingDto") @Valid TrainingDto trainingDto,
                             BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(trainingDto,request), bindingResult);
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

            return viewSuccess(trainingDto,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_TRAINING);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_TRAINING_SEARCH,ROLE_TRAINING_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id,HttpServletRequest request){
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

        return view(trainingDto,request);
    }

    private ModelAndView formAdd(TrainingDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView formEdit(TrainingDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "แก้ไข");
    }


    private ModelAndView form(TrainingDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_TRAINING_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("training", data);
        return view;
    }

    private ModelAndView viewSuccess(TrainingDto data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(TrainingDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_TRAINING_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("training", data);
        return view;
    }

    @RolesAllowed({ROLE_TRAINING_EDIT,ROLE_TRAINING_ADD})
    @RequestMapping(path = "/addPhase",method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView addPhase(@ModelAttribute("trainingDto") TrainingDto trainingDto,HttpServletRequest request) {

        List<TrainingPhase> phases = trainingDto.getPhases();

        TrainingPhase phase = new TrainingPhase();
        phase.setTrainingPhase(phases.size()+1);

        phases.add(phase);

        trainingDto.setPhases(phases);

        return formEdit(trainingDto,request);
    }

    @RolesAllowed({ROLE_TRAINING_EDIT,ROLE_TRAINING_ADD})
    @RequestMapping(path = "/removePhase/{phase}", method = { RequestMethod.POST}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView removePhase(@ModelAttribute("trainingDto") TrainingDto trainingDto,
                                    @PathVariable String phase,
                                    HttpServletRequest request) {

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

        return formEdit(trainingDto,request);
    }
}
