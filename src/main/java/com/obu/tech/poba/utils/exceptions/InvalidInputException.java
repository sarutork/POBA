package com.obu.tech.poba.utils.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
public class InvalidInputException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "ข้อมูลที่ป้อนไม่ถูกต้อง";
    private static final String DEFAULT_VIEW = "home";

    private ModelAndView redirect;
    private final List<String> errors = new ArrayList<>();

    public InvalidInputException() {
        initialize();
    }

    public InvalidInputException(ModelAndView redirect, BindingResult bindingResult) {
        if (nonNull(redirect) && redirect.hasView()) {
            this.redirect = redirect;
            setErrors(bindingResult);
        } else initialize();
    }

    private void setErrors(BindingResult bindingResult) {
        if (nonNull(bindingResult) && bindingResult.hasFieldErrors()) {
            for (FieldError e : bindingResult.getFieldErrors()) {
                log.error("{}.{}: {}", bindingResult.getObjectName(), e.getField(), e.getDefaultMessage());
                errors.add(e.getDefaultMessage());
            }
            log.error("redirect: " + redirect.getViewName());
            redirect.addObject("responseErrors", errors);
        } else errors.add(DEFAULT_MESSAGE);
    }

    private void initialize() {
        redirect = new ModelAndView(DEFAULT_VIEW)
                .addObject("responseMessage", DEFAULT_MESSAGE);
    }
}
