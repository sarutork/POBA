package com.obu.tech.poba.lecturer;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.resolution.Resolution;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.YearGeneratorUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import com.obu.tech.poba.utils.upload.Upload;
import com.obu.tech.poba.utils.upload.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.*;
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_LECTURER;
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_RESOLUTION;

@Slf4j
@Controller
@RolesAllowed(ROLE_LECTURER_ACCESS)
@RequestMapping("/lecturer")
public class LecturerController {
    static final String FRAGMENT_LECTURER_INFO = "lecturer-info/lecturer :: lecturer";
    static final String FRAGMENT_LECTURER_FORM = "lecturer-info/lecturer-form :: lecturer-form";


    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private UploadService uploadService;

    @Value("${poba.upload.lecturer}")
    private String UPLOAD_LECTURER_PATH;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_LECTURER_INFO);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        return view;
    }

    @RolesAllowed(ROLE_LECTURER_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Lecturer>> search(@ModelAttribute Lecturer lecturer) {
        return ResponseEntity.ok().body(lecturerService.findBySearchCriteria(lecturer));
    }

    @RolesAllowed(ROLE_LECTURER_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new Lecturer(),request);}

    @RolesAllowed({ROLE_LECTURER_ADD,ROLE_LECTURER_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("teaching") @Valid Lecturer lecturer,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(lecturer,request), bindingResult);
        }
        try{
            long lecturerId = lecturer.getLecturerId();
            if(lecturerId != 0) {
                List<Upload> remains = uploadService.delete(
                        lecturer.getFilesToKeep(),
                        UPLOAD_GROUP_LECTURER,
                        lecturerId
                );
                List<Upload> uploads = uploadService.upload(
                        lecturer.getNewFiles(),
                        UPLOAD_GROUP_LECTURER,
                        lecturerId,
                        UPLOAD_LECTURER_PATH
                );
                remains.addAll(uploads);
                lecturer.setUploads(remains);
            }

            Lecturer lecturerRes = lecturerService.save(lecturer);
            lecturerRes.setPrefix(lecturer.getPrefix());
            lecturerRes.setName(lecturer.getName());

            if(lecturerId == 0) {
                List<Upload> uploads = uploadService.upload(
                        lecturer.getNewFiles(),
                        UPLOAD_GROUP_LECTURER,
                        lecturerRes.getLecturerId(),
                        UPLOAD_LECTURER_PATH
                );
            }

            List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_LECTURER, lecturerRes.getLecturerId());
            lecturerRes.setUploads(uploads);

            return viewSuccess(lecturerRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_LECTURER_INFO).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @RolesAllowed(ROLE_LECTURER_EDIT)
    @GetMapping(value = "/{id}")
    public ModelAndView showLecturerInfo(@PathVariable String id,HttpServletRequest request){
        Lecturer lecturer = lecturerService.findById(id);
        Profile profile = profileService.findByPersNo(lecturer.getPersNo());
        lecturer.setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        lecturer.setName(profile.getName()+" "+profile.getSurname());

        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_LECTURER, lecturer.getLecturerId());
        lecturer.setUploads(uploads);
        return view(lecturer,request);
    }

    private ModelAndView formAdd(Lecturer data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Lecturer data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_LECTURER_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        view.addObject("lecturer", data);
        view.addObject("years", years);
        return view;
    }

    private ModelAndView viewSuccess(Lecturer data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Lecturer data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_LECTURER_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("lecturer", data);
        return view;
    }
}
