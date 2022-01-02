package com.obu.tech.poba.controllers.api;

import com.obu.tech.poba.dao.StudyInfoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personnel-info")
public class PersonnelInfoAPIController {
    @Autowired
    StudyInfoDao studyInfoDao;

    @GetMapping(value = {"/education-info", "/education-info/{id}"})
    public List<Map<String,Object>> educationInfo(HttpServletRequest request, @PathVariable(required = false) String id) throws Exception {
        System.out.println("id: "+id);
        List<Map<String,Object>> educationInfoList = null;
        if(StringUtils.isBlank(id)){
            educationInfoList = studyInfoDao.findAll();
        }
        System.out.println(educationInfoList.get(0).get("name"));

        return educationInfoList;
    }
}
