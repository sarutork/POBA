package com.obu.tech.poba.utils.exceptions;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class InvalidInputException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid input data";

    private final ModelAndView redirect; // add/update failed -> redirect back to this view
    private final Object object; // add/update data
    private String objectName; // model name
    private List<String> errors;

    private void setErrors(BindingResult bindingResult) {
        if (Objects.nonNull(bindingResult) && bindingResult.hasFieldErrors()) {
            this.objectName = bindingResult.getObjectName();
            this.errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(e -> errors.add(e.getDefaultMessage()));
        }
    }

    public InvalidInputException(ModelAndView redirect, Object object, BindingResult bindingResult) {
        this.redirect = redirect;
        this.object = object;
        setErrors(bindingResult);
    }
}
