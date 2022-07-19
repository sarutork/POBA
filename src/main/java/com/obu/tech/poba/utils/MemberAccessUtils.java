package com.obu.tech.poba.utils;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.authenticate.POBAUser;
import com.obu.tech.poba.authenticate.POBAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberAccessUtils {
    @Autowired
    private POBAUserService pobaUserService;
    public MemberAccess getMemberAccess(HttpServletRequest request) {
        MemberAccess member = new MemberAccess();
        POBAUser user = (POBAUser) request.getSession().getAttribute("poba-user");
        if(user != null) {
            user.setPassword("xxxxxx");
            List<String> roles = pobaUserService.rules(user.getUsername());
            member.setMember(user);
            member.setRoles(roles);
        }else{
            member.setRoles(new ArrayList<>());
        }
        return member;
    }
}
