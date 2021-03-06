package com.obu.tech.poba.teaching_info;

import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
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
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_TEACHING;

@Slf4j
@Controller
@RequestMapping("/teaching")
public class TeachingController {
    static final String FRAGMENT_TEACHING_INFO = "teaching-info/teaching :: teaching";
    static final String FRAGMENT_TEACHING_FORM = "teaching-info/teaching-form :: teaching-form";

    @Autowired
    private TeachingService teachingService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private UploadService uploadService;

    @Autowired
    ProfileService profileService;

    @Value("${poba.upload.teaching}")
    private String UPLOAD_TEACHING_PATH;

    @GetMapping
    public ModelAndView showListView() {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_TEACHING_INFO).addObject("years", years);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Teaching>> search(@ModelAttribute Teaching teaching) {
        return ResponseEntity.ok().body(teachingService.findBySearchCriteria(teaching));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new Teaching());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("teaching") @Valid Teaching teaching, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(teaching), bindingResult);
        }
        try{
            long staffId = teaching.getStaffId();
            if(staffId != 0) {
                List<Upload> remains = uploadService.delete(
                        teaching.getFilesToKeep(),
                        UPLOAD_GROUP_TEACHING,
                        staffId
                );
                List<Upload> uploads = uploadService.upload(
                        teaching.getNewFiles(),
                        UPLOAD_GROUP_TEACHING,
                        staffId,
                        UPLOAD_TEACHING_PATH
                );
                remains.addAll(uploads);
                teaching.setUploads(remains);
            }

            Teaching teachingRes = teachingService.save(teaching);
            teachingRes.setPrefix(teaching.getPrefix());
            teachingRes.setName(teaching.getName());

            if(staffId == 0) {
                List<Upload> uploads = uploadService.upload(
                        teaching.getNewFiles(),
                        UPLOAD_GROUP_TEACHING,
                        teachingRes.getStaffId(),
                        UPLOAD_TEACHING_PATH
                );
            }

            List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_TEACHING, teachingRes.getStaffId());
            teachingRes.setUploads(uploads);

            return viewSuccess(teachingRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_TEACHING_INFO).addObject("responseMessage", "???????????????????????????");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        Teaching teaching = teachingService.findById(id);
        Profile profile = profileService.findByPersNo(teaching.getPersNo());
        teaching.setPrefix(profile.getPrefix().equals("???????????????")? profile.getPrefixOther() : profile.getPrefix());
        teaching.setName(profile.getName()+" "+profile.getSurname());

        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_TEACHING, teaching.getStaffId());
        teaching.setUploads(uploads);

        return view(teaching);
    }

    private ModelAndView formAdd(Teaching data) {
        return form(data).addObject("viewName", "?????????????????????????????????");
    }

    private ModelAndView form(Teaching data) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_TEACHING_FORM).addObject("teaching", data).addObject("years", years);
    }

    private ModelAndView viewSuccess(Teaching data) {
        return view(data)
                .addObject("viewName", "????????????????????????")
                .addObject("responseMessage", "????????????????????????????????????")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Teaching data) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_TEACHING_FORM).addObject("viewName", "????????????????????????")
                                                       .addObject("teaching", data)
                                                       .addObject("years", years);
    }
}
