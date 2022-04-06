package com.obu.tech.poba.personnel_info.profile;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;
@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    List<Profile> findBySearchCriteria(Profile profile){
        return profileRepository.findAll(new SearchConditionBuilder<Profile>()
                .ifNotNullThenAnd("name", LIKE, profile.getName())
                .ifNotNullThenOr("surname", LIKE, profile.getName())
                .build()
        );
    }

    public Profile save(Profile profile) {
        return profileRepository.saveAndFlush(profile);
    }

    public Profile findById(String id) {
        if (id.matches("\\d+")) {
            return profileRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
