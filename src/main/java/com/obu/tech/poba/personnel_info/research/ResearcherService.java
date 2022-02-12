package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.utils.exceptions.NotFoundException;
import com.obu.tech.poba.utils.exceptions.UploadException;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import com.obu.tech.poba.utils.upload.Upload;
import com.obu.tech.poba.utils.upload.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        return researcherRepository
                .findAll(new SearchConditionBuilder<Researcher>()
                        .ifNotNullThenAnd("name", LIKE, researcher.getName())
                        .ifNotNullThenOr("surname", LIKE, researcher.getName())
                        .ifNotNullThenAnd("workStartDate", DATE_AFTER_OR_EQUAL, researcher.getWorkStartDate())
                        .ifNotNullThenAnd("workEndDate", DATE_BEFORE_OR_EQUAL, researcher.getWorkEndDate())
                        .build());
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
        splitSurname(researcher);

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
        researcher.setPrefix        (updateData.getPrefix());
        researcher.setName          (updateData.getName()); // full name
        splitSurname(researcher);
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

    private void splitSurname(Researcher researcher) {
        String fullName = researcher.getName().replaceAll("\\s+", " ").trim();
        int firstSpace = fullName.contains(" ") ? fullName.indexOf(" ") : fullName.length();
        researcher.setName(fullName.substring(0, firstSpace));
        researcher.setSurname(fullName.substring(firstSpace).trim());
    }
}
