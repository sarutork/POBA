package com.obu.tech.poba.project;

import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/project")
public class ProjectController {
    static final String FRAGMENT_PROJECT = "project/project :: project";
    static final String FRAGMENT_PROJECT_FORM = "project/project-form :: project-form";

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_PROJECT);}

    @GetMapping("/search")
    public ResponseEntity<List<Project>> search(@ModelAttribute Project project) {
        return ResponseEntity.ok().body(projectService.findBySearchCriteria(project));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new Project());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(project), bindingResult);
        }
        try{
            Project projectRes = projectService.save(project);
            return viewSuccess(projectRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_PROJECT_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        return view(projectService.findById(id));
    }

    private ModelAndView formAdd(Project data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Project data) {
        return new ModelAndView(FRAGMENT_PROJECT_FORM).addObject("project", data);
    }

    private ModelAndView viewSuccess(Project data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Project data) {
        return new ModelAndView(FRAGMENT_PROJECT_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("project", data);
    }
}
