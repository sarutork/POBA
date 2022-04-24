package com.obu.tech.poba.consultant_info.thesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AcademicConfService {

    @Autowired
    AcademicConfRepository academicConfRepository;

    public AcademicConference save(AcademicConference academicConference) {
        return academicConfRepository.saveAndFlush(academicConference);
    }

    public AcademicConference findByThesisId(String id) {
        if (id.matches("\\d+")) {
            return academicConfRepository.findByThesisId(Long.parseLong(id));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
