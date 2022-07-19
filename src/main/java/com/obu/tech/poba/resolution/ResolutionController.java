package com.obu.tech.poba.resolution;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import com.obu.tech.poba.utils.upload.Upload;
import com.obu.tech.poba.utils.upload.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.*;
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_RESOLUTION;

@Slf4j
@Controller
@RolesAllowed(ROLE_RESOLUTION_ACCESS)
@RequestMapping("/resolution")
public class ResolutionController {
    static final String FRAGMENT_RESOLUTION = "resolutions/resolution :: resolution";
    static final String FRAGMENT_RESOLUTION_FORM = "resolutions/resolution-form :: resolution-form";
    static final String FRAGMENT_RESOLUTION_VIEW = "resolutions/resolution-view :: resolution-view";

    @Autowired
    private ResolutionService resolutionService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private UploadService uploadService;

    @Value("${poba.upload.resolution}")
    private String UPLOAD_RESOLUTION_PATH;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESOLUTION);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_RESOLUTION_ADD)
    @GetMapping(value = "/add")
    public ModelAndView showAddView(HttpServletRequest request) {return formAdd(new Resolution(),request);}

    @RolesAllowed({ROLE_RESOLUTION_SEARCH,ROLE_RESOLUTION_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        Resolution resolution = resolutionService.findById(id);
        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_RESOLUTION, resolution.getBordId());
        resolution.setUploads(uploads);
        return view(resolution,request);
    }

    @RolesAllowed({ROLE_RESOLUTION_SEARCH,ROLE_RESOLUTION_EDIT})
    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESOLUTION_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "แก้ไขข้อมูล");
        Resolution resolution = resolutionService.findById(id);
        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_RESOLUTION, resolution.getBordId());
        resolution.setUploads(uploads);
        view.addObject("resolution",resolution);
        return view;
    }

    @RolesAllowed(ROLE_RESOLUTION_SEARCH)
    @GetMapping(value = "/search")
    public ResponseEntity<List<Resolution>> search(String bordNo,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dateStart ,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dateEnd) {
        String bordNo1 = null, bordNo2 = null;

        if(StringUtils.isNotEmpty(bordNo)) {
            String bordNoArr[] = bordNo.trim().split("/");
            bordNo1 = bordNoArr[0];
            if(bordNoArr.length > 1){
                bordNo2 = bordNoArr[1];
            }
        }

        return ResponseEntity.ok().body(resolutionService.findBySearchCriteria(bordNo1,bordNo2,dateStart,dateEnd));
    }

    @RolesAllowed({ROLE_RESOLUTION_EDIT,ROLE_RESOLUTION_ADD})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("resolution") @Valid Resolution resolution,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(resolution,request), bindingResult);
        }
        try {
            long bordId = resolution.getBordId();

            if(bordId != 0) {
                List<Upload> remains = uploadService.delete(
                        resolution.getFilesToKeep(),
                        UPLOAD_GROUP_RESOLUTION,
                        bordId
                );
                List<Upload> uploads = uploadService.upload(
                        resolution.getNewFiles(),
                        UPLOAD_GROUP_RESOLUTION,
                        bordId,
                        UPLOAD_RESOLUTION_PATH
                );
                remains.addAll(uploads);
                resolution.setUploads(remains);
            }

            Resolution resolutionRes = resolutionService.save(resolution);

            if(bordId == 0) {
                List<Upload> uploads = uploadService.upload(
                        resolution.getNewFiles(),
                        UPLOAD_GROUP_RESOLUTION,
                        resolutionRes.getBordId(),
                        UPLOAD_RESOLUTION_PATH
                );
            }

            List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_RESOLUTION, resolutionRes.getBordId());
            resolutionRes.setUploads(uploads);

            return viewSuccess(resolutionRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_RESOLUTION);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    private ModelAndView formAdd(Resolution data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Resolution data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESOLUTION_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("resolution", data);
        return view;
    }

    private ModelAndView viewSuccess(Resolution data,HttpServletRequest request) {
        return view(data,request)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView viewError(Resolution data, String message,HttpServletRequest request) {
        return view(data,request).addObject("responseMessage", message);
    }

    private ModelAndView view(Resolution data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESOLUTION_VIEW);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("resolution", data);
        return view;
    }

}
