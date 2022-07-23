package com.obu.tech.poba.training;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
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

    @Autowired
    ProfileService profileService;

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

            Training training = new Training();

            BeanUtils.copyProperties(trainingDto,training);

            int sumAmount = 0;
            for(TrainingPhase phase: trainingDto.getPhases()){
                sumAmount += phase.getTrainingAmount();
            }
            training.setTrainingAmountTotal(sumAmount);

            Training trainingRes = trainingService.save(training);

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

        Profile profile = profileService.findByPersNo(training.getPersNo1());
        training.setPrefix1(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        training.setName1(profile.getName()+" "+profile.getSurname());

        if(StringUtils.isNotEmpty(training.getPersNo2())) {
            Profile profile2 = profileService.findByPersNo(training.getPersNo2());
            training.setPrefix2(profile2.getPrefix().equals("อื่นๆ")? profile2.getPrefixOther() : profile2.getPrefix());
            training.setName2(profile2.getName()+" "+profile2.getSurname());
        }
        if(StringUtils.isNotEmpty(training.getPersNo3())) {
            Profile profile3 = profileService.findByPersNo(training.getPersNo3());
            training.setPrefix3(profile3.getPrefix().equals("อื่นๆ")? profile3.getPrefixOther() : profile3.getPrefix());
            training.setName3(profile3.getName()+" "+profile3.getSurname());
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
