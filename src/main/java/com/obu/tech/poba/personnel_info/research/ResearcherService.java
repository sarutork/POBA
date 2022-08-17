package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.utils.exceptions.NotFoundException;
import com.obu.tech.poba.utils.exceptions.UploadException;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import com.obu.tech.poba.utils.upload.Upload;
import com.obu.tech.poba.utils.upload.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.obu.tech.poba.utils.search.SearchOperator.*;
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_RESEARCHER;

@Slf4j
@Service
public class ResearcherService {

    @Value("${poba.upload.researcher}")
    private String UPLOAD_RESEARCHER_PATH;

    private static final String UPLOAD_GROUP = "researcher";

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ResearcherRepository researcherRepository;

    public List<Researcher> search(Researcher researcher) {

        List<Object[]> data = researcherRepository.findInfo("%"+researcher.getName()+"%",researcher.getWorkStartDate(),researcher.getWorkEndDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Researcher> researchers = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Researcher result = new Researcher();
                result.setStaffId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setStatus(e[5].toString());
                }

                if(e[6] != null){
                    result.setType(e[6].toString());
                }

                if(e[7] != null){
                    result.setTeacher1(e[7].toString());
                }

                if(e[8] != null){
                    result.setTeacher2(e[8].toString());
                }

                if(e[9] != null){
                    result.setSubSegment(e[9].toString());
                }

                if(e[10] != null){
                    result.setWorkStartDate(LocalDate.parse(e[10].toString(),formatter));
                }
                if(e[11] != null){
                    result.setWorkEndDate(LocalDate.parse(e[11].toString(),formatter));
                }

                if(e[12] != null){
                    result.setNoteOfWork(e[12].toString());
                }

                researchers.add(result);
            }
        }
        return researchers;
    }

    public List<Researcher> findAll() {
        return researcherRepository.findAll();
    }

    public Researcher findById(String id) {
        if (id.matches("\\d+")) {
            Optional<Researcher> optional = researcherRepository.findById(Long.parseLong(id));
            if (optional.isPresent()) {
                Researcher researcher = optional.get();
                List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP, researcher.getStaffId());
                researcher.setUploads(uploads);
                return researcher;
            } else throw new NotFoundException("Not found Researcher.staffId [" + id + "]");
        } else throw new NotFoundException("Invalid Researcher.staffId [" + id + "]");
    }

    public Researcher add(Researcher researcher) {

        researcherRepository.saveAndFlush(researcher); // generate staff_id for upload.reference_key

        List<Upload> uploads = uploadService.upload(
                researcher.getNewFiles(),
                UPLOAD_GROUP_RESEARCHER,
                researcher.getStaffId(),
                UPLOAD_RESEARCHER_PATH
        );
        researcher.setUploads(uploads);

        log.info("Add " + researcher);
        return researcher;
    }

    public Researcher update(String id, Researcher updateData) throws NotFoundException, UploadException {
        Researcher researcher = findById(id);
        researcher.setPersNo(updateData.getPersNo());
        researcher.setStatus        (updateData.getStatus());
        researcher.setType          (updateData.getType());
        researcher.setWorkStartDate (updateData.getWorkStartDate());
        researcher.setWorkEndDate   (updateData.getWorkEndDate());
        researcher.setTeacher1      (updateData.getTeacher1());
        researcher.setTeacher2      (updateData.getTeacher2());
        researcher.setSubSegment    (updateData.getSubSegment());
        researcher.setNoteOfWork    (updateData.getNoteOfWork());

        List<Upload> remains = uploadService.delete(
                updateData.getFilesToKeep(),
                UPLOAD_GROUP_RESEARCHER,
                researcher.getStaffId()
        );
        List<Upload> uploads = uploadService.upload(
                updateData.getNewFiles(),
                UPLOAD_GROUP_RESEARCHER,
                researcher.getStaffId(),
                UPLOAD_RESEARCHER_PATH
        );
        remains.addAll(uploads);
        researcher.setUploads(remains);

        log.info("Update " + researcher);
        return researcherRepository.save(researcher);
    }
}
