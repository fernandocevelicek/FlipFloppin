package com.grupo1.FlipFloppin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(ModelMap modelo, @RequestParam(required=false) String error, @RequestParam(required=false) String ok) {
        if (error != null) {
            modelo.put("error", error);
        }
        if (ok != null) {
            modelo.put("ok", ok);
        }
        return "login.html";
    }

}
