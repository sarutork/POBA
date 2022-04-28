package com.obu.tech.poba.resolution;

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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_RESOLUTION;

@Slf4j
@Controller
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

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(FRAGMENT_RESOLUTION);
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() {return formAdd(new Resolution());}

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        Resolution resolution = resolutionService.findById(id);
        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_RESOLUTION, resolution.getBordId());
        resolution.setUploads(uploads);
        return view(resolution);
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESOLUTION_FORM);
        view.addObject("viewName", "แก้ไขข้อมูล");
        Resolution resolution = resolutionService.findById(id);
        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_RESOLUTION, resolution.getBordId());
        resolution.setUploads(uploads);
        view.addObject("resolution",resolution);
        return view;
    }

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

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("resolution") @Valid Resolution resolution, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(resolution), bindingResult);
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

            return viewSuccess(resolutionRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_RESOLUTION).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    private ModelAndView formAdd(Resolution data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Resolution data) {
        return new ModelAndView(FRAGMENT_RESOLUTION_FORM).addObject("resolution", data);
    }

    private ModelAndView viewSuccess(Resolution data) {
        return view(data)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView viewError(Resolution data, String message) {
        return view(data).addObject("responseMessage", message);
    }

    private ModelAndView view(Resolution data) {
        return new ModelAndView(FRAGMENT_RESOLUTION_VIEW).addObject("resolution", data);
    }

}
