package com.superbleep.rvgamvc.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("messages", e.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));
        modelAndView.addObject("referer", referer != null ? referer : "/");

        logger.error(e.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList().toString());

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("messages", e.getMessage());
        modelAndView.addObject("referer", referer != null ? referer : "/");

        logger.error(e.getMessage());

        return modelAndView;
    }
}
