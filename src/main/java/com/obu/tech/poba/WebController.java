package com.obu.tech.poba;

import com.obu.tech.poba.authenticate.POBAUser;
import com.obu.tech.poba.authenticate.POBAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class WebController {

    private boolean isShowBtnUpdate = false;

    @Autowired
    private POBAUserService pobaUserService;

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
        return view;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody POBAUser user,
                                          HttpServletRequest request) throws Exception {
        if(!pobaUserService.getUserBy(user).isPresent())
            return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);

        request.getSession().setAttribute("poba-user",user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
