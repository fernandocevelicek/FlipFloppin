package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static com.grupo1.FlipFloppin.utils.Constants.BASE_VIEW_PATH;

@Controller
public class IndexController {

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        if(session.getAttribute("usuario_session") != null) {
            Usuario user = (Usuario) session.getAttribute("usuario_session");
            modelo.put("username", user.getNombre());
        }

        return BASE_VIEW_PATH + "index.html";
    }

    @GetMapping("/inicio")
    public String inicio(ModelMap modelo) {
        if(session.getAttribute("usuario_session") != null) {
            Usuario user = (Usuario) session.getAttribute("usuario_session");
            modelo.put("username", user.getNombre());
        }

        return BASE_VIEW_PATH + "index.html";
    }

}
