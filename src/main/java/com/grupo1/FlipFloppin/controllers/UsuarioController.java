package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService userService;


        @PostMapping("/bajaUser{id}")
        public String bajarUsuario(Model model, @PathVariable("id") long id) {

            try {
                this.userService.deleteById(id);
                return "redirect:/.....";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
    }
}
