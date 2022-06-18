package com.obu.tech.poba.lecturer;

import com.obu.tech.poba.resolution.Resolution;
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
import java.util.List;

import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_LECTURER;
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_RESOLUTION;

@Slf4j
@Controller
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

    @GetMapping
    public ModelAndView showListView() {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_LECTURER_INFO).addObject("years", years);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Lecturer>> search(@ModelAttribute Lecturer lecturer) {
        return ResponseEntity.ok().body(lecturerService.findBySearchCriteria(lecturer));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new Lecturer());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("teaching") @Valid Lecturer lecturer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(lecturer), bindingResult);
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

            if(!StringUtils.isBlank(lecturer.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(lecturer.getName());
                lecturer.setName(fullName[0]);
                lecturer.setSurname(fullName[1]);
            }

            Lecturer lecturerRes = lecturerService.save(lecturer);
            lecturerRes.setName(lecturerRes.getName()+" "+lecturerRes.getSurname());

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

            return viewSuccess(lecturerRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_LECTURER_INFO).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showLecturerInfo(@PathVariable String id){
        Lecturer lecturer = lecturerService.findById(id);
        lecturer.setName(lecturer.getName()+" "+lecturer.getSurname());
        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_LECTURER, lecturer.getLecturerId());
        lecturer.setUploads(uploads);
        return view(lecturer);
    }

    private ModelAndView formAdd(Lecturer data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Lecturer data) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_LECTURER_FORM).addObject("lecturer", data).addObject("years", years);
    }

    private ModelAndView viewSuccess(Lecturer data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Lecturer data) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_LECTURER_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("lecturer", data)
                .addObject("years", years);
    }
}
