package com.obu.tech.poba.utils;

import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import com.obu.tech.poba.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showNotFoundView(NotFoundException ex) {
        if (isNotBlank(ex.getMessage())) log.error("[NotFoundException] " + ex.getMessage());
        return new ModelAndView("404");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public ModelAndView handleInvalidInput(InvalidInputException ex) {
        return ex.getRedirect();
    }
}
