package com.obu.tech.poba.authenticate;

import com.obu.tech.poba.press_info.Press;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.obu.tech.poba.utils.search.SearchOperator.EQUAL;

@Service
public class POBAUserService {
    @Autowired
    private POBAUserRepository pobaUserRepository;
    @Autowired
    private POBAUserRolesRepository pobaUserRolesRepository;

    public Optional<POBAUser> getUserBy(POBAUser user){
        return pobaUserRepository.findOne(new SearchConditionBuilder<POBAUser>()
                .ifNotNullThenAnd("username", EQUAL, user.getUsername())
                .ifNotNullThenAnd("password", EQUAL, user.getPassword())
                .build()
        );
    }

    public List<String> rules(String username){
        List<String> roles = new ArrayList<>();
        pobaUserRolesRepository.findRoles(username)
                .stream()
                .filter(s->(s != null))
                .forEach(obj->roles.add("ROLE_".concat(obj[0].toString())));
        return roles;
    }

    public POBAUser save(POBAUser pobaUser){
        return pobaUserRepository.saveAndFlush(pobaUser);
    }

    public List<POBAUser> findAll(){
        return pobaUserRepository.findAll();
    }

    public POBAUser findById(String id) {
        if (id.matches("\\d+")) {
            return pobaUserRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
