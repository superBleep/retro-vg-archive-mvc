package com.superbleep.rvgamvc.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("messages", e.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));
        modelAndView.addObject("referer", referer != null ? referer : "/emulators");

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("messages", e.getMessage());
        modelAndView.addObject("referer", referer != null ? referer : "/emulators");

        return modelAndView;
    }
}
