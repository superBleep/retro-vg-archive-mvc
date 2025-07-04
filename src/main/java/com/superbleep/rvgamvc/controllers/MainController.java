package com.superbleep.rvgamvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String showLogInForm(){
        return "login";
    }

    @GetMapping("/logout")
    public String showLogOutConfirmation() {
        return "logout";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage(){
        return "accessDenied";
    }
}
