package com.obu.tech.poba.project;

import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.students.Students;
import com.obu.tech.poba.students.StudentsService;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectController {
    static final String FRAGMENT_PROJECT = "project/project :: project";
    static final String FRAGMENT_PROJECT_FORM = "project/project-form :: project-form";

    @Autowired
    private ProjectService projectService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_PROJECT);}

    @GetMapping("/search")
    public ResponseEntity<List<Project>> search(@ModelAttribute Project project) {
        return ResponseEntity.ok().body(projectService.findBySearchCriteria(project));
    }

    @GetMapping("/search-participant")
    public ResponseEntity<List<Participant>> searchParticipant(String searchTxt) {
        List<Students> students = studentsService.findByNameOrId(searchTxt);
        List<Participant> participants = new ArrayList<>();
        for (Students s: students) {
            Participant p = new Participant();

            p.setParticipantId(s.getStudentsId());

            String prefix = s.getPrefix();
            if(prefix !=null && "อื่นๆ".equals(s.getPrefix())){
                prefix = s.getPrefixOther();
            }
            p.setName(prefix+" "+s.getName()+" "+s.getSurname());

            participants.add(p);
        }

        List<Profile> profiles = profileService.findByNameOrId(searchTxt);

        for (Profile pr: profiles) {
            Participant p = new Participant();

            p.setParticipantId(pr.getPersNo());

            String prefix = pr.getPrefix();
            if(prefix !=null && "อื่นๆ".equals(pr.getPrefix())){
                prefix = pr.getPrefixOther();
            }
            p.setName(prefix+" "+pr.getName()+" "+pr.getSurname());

            participants.add(p);
        }

        return ResponseEntity.ok().body(participants);
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ProjectDto projectDto = new ProjectDto();
        return formAdd(projectDto);

    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("projectDto") @Valid ProjectDto projectDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(projectDto), bindingResult);
        }

        try{
            Project project = new Project();
            BeanUtils.copyProperties(projectDto, project);
            Project projectRes = projectService.save(project);

            if(projectDto.getParticipants() != null && projectDto.getParticipants().size() !=0) {
                long projectId = projectRes.getProjectId();
                List<Participant> participants = projectDto.getParticipants();

                participants.removeIf(p -> (StringUtils.isBlank(p.getParticipantId())));

                participants.forEach(p -> p.setProjectId(projectId));

                projectService.removeParticipantByProjectId(projectId);
                projectService.saveParticipant(participants);

            }

            return viewSuccess(projectDto);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_PROJECT_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        Project project = projectService.findById(id);
        List<Participant> participants = projectService.findByProjectId(id);

        ProjectDto projectDto = new ProjectDto();
        BeanUtils.copyProperties(project,projectDto);
        if(participants.size() != 0){
            projectDto.setParticipants(participants);
        }
        return view(projectDto);
    }

    private ModelAndView formAdd(ProjectDto data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ProjectDto data) {
        return new ModelAndView(FRAGMENT_PROJECT_FORM)
                .addObject("project", data);
    }

    private ModelAndView viewSuccess(ProjectDto data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ProjectDto data) {
        return new ModelAndView(FRAGMENT_PROJECT_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("project", data);
    }
}
