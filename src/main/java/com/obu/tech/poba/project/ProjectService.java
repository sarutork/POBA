package com.obu.tech.poba.project;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.DATE_AFTER_OR_EQUAL;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;
@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ParticipantRepository participantRepository;

    List<Project> findBySearchCriteria(Project project){
        return projectRepository.findAll(new SearchConditionBuilder<Project>()
                .ifNotNullThenAnd("projectName", LIKE, project.getProjectName())
                .ifNotNullThenAnd("projectYear", LIKE, project.getProjectYear())
                .ifNotNullThenAnd("projectDateFrom", DATE_AFTER_OR_EQUAL, project.getProjectDateFrom())
                .build()
        );
    }

    public Project save(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public Project findById(String id) {
        if (id.matches("\\d+")) {
            return projectRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Participant> saveParticipant(List<Participant> participants){
        return  participantRepository.saveAllAndFlush(participants);
    }

    public List<Participant> findByProjectId(String id) {
        if (id.matches("\\d+")) {
            return participantRepository.findByProjectId(Long.parseLong(id));
        } else {
            System.out.println("Invalid project_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public void removeParticipantByProjectId(Long id){
         participantRepository.deleteByProjectId(id);
    }
}
