package com.obu.tech.poba.personnel_info.profile;

import com.obu.tech.poba.teaching_info.Teaching;
import com.obu.tech.poba.teaching_info.TeachingRepository;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
