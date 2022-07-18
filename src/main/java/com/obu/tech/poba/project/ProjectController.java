package com.obu.tech.poba.project;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.students.Students;
import com.obu.tech.poba.students.StudentsService;
import com.obu.tech.poba.utils.MemberAccessUtils;
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
@RolesAllowed(ROLE_PROJECT_ACCESS)
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

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_PROJECT);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        return view;
    }

    @RolesAllowed(ROLE_PROJECT_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Project>> search(@ModelAttribute Project project) {
        return ResponseEntity.ok().body(projectService.findBySearchCriteria(project));
    }

    @RolesAllowed({ROLE_PROJECT_SEARCH,ROLE_PROJECT_ADD,ROLE_PROJECT_EDIT})
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

    @RolesAllowed(ROLE_PROJECT_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        ProjectDto projectDto = new ProjectDto();
        return formAdd(projectDto,request);

    }

    @RolesAllowed({ROLE_PROJECT_ADD,ROLE_PROJECT_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("projectDto") @Valid ProjectDto projectDto,
                             BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(projectDto,request), bindingResult);
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

            return viewSuccess(projectDto,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_PROJECT_FORM);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_PROJECT_SEARCH,ROLE_PROJECT_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        Project project = projectService.findById(id);
        List<Participant> participants = projectService.findByProjectId(id);

        ProjectDto projectDto = new ProjectDto();
        BeanUtils.copyProperties(project,projectDto);
        if(participants.size() != 0){
            projectDto.setParticipants(participants);
        }
        return view(projectDto,request);
    }

    private ModelAndView formAdd(ProjectDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ProjectDto data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_PROJECT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        view.addObject("project", data);
        return view;

    }

    private ModelAndView viewSuccess(ProjectDto data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ProjectDto data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_PROJECT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("years", years);
        view.addObject("project", data);
        return view;



    }
}
