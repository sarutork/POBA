package com.obu.tech.poba;

import com.obu.tech.poba.authenticate.*;
import com.obu.tech.poba.utils.MemberAccessUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class WebController {

    private boolean isShowBtnUpdate = false;

    @Autowired
    private POBAUserService pobaUserService;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping("/")
    public ModelAndView index() throws Exception {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) throws Exception {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView("home");
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody POBAUser user,
                                          HttpServletRequest request) throws Exception {
        Optional<POBAUser> authen = pobaUserService.getUserBy(user);
        if(!authen.isPresent())
            return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);

        POBAUser member = authen.orElse(new POBAUser());
        member.setPassword("xxx");
        request.getSession().setAttribute("poba-user",member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    @RequestMapping(value = "/forgot/password", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> forgotPassword(@RequestBody POBAUser user,
                                          HttpServletRequest request) throws Exception {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @RequestMapping(value = "/reset/password", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword pass,
                                            HttpServletRequest request) throws Exception {
        AuthenticateMessageHandle messageHandle = new AuthenticateMessageHandle();
        if(StringUtils.isNotBlank(pass.getNewPassword())
                && StringUtils.isNotBlank(pass.getConfirmPassword())
                && pass.getNewPassword().equals(pass.getConfirmPassword())){
            POBAUser user = (POBAUser)request.getSession().getAttribute("poba-user");
            user.setResetPassword("N");
            user.setPassword(pass.getNewPassword());
            System.out.println(user);
            pobaUserService.save(user);
            messageHandle.setStatus("success");
            messageHandle.setMessage("ดำเนินการสำเร็จ");
            return new ResponseEntity<>(messageHandle, HttpStatus.OK);
        }else{
            messageHandle.setStatus("error");
            messageHandle.setMessage("กรุณาตรวจสอบข้อมูลรหัสผ่าน");
            return new ResponseEntity<>(messageHandle, HttpStatus.BAD_REQUEST);
        }
    }
}
