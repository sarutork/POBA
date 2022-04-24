package com.obu.tech.poba.consultant_info.thesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class JournalService {
    @Autowired
    JournalRepository journalRepository;

    public Journal save(Journal journal) {
        return journalRepository.saveAndFlush(journal);
    }

    public Journal findByThesisId(String id) {
        if (id.matches("\\d+")) {
            return journalRepository.findByThesisId(Long.parseLong(id));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
