package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.grupo1.FlipFloppin.utils.Constants.VIEW_PATH;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro_cliente")
    public String registroCliente(ModelMap modelo){
        return VIEW_PATH + "registro_cliente.html";
    }

    @PostMapping("/registrar_cliente")
    public String registrarCliente(ModelMap modelo,
                                   @RequestParam(required=true) String nombre,
                                   @RequestParam(required=true) String apellido,
                                   @RequestParam(required=true) String password,
                                   @RequestParam(required=true) String email,
                                   @RequestParam(required=true) String password_confirmation) throws Exception {

        usuarioService.registrarUsuario(nombre, apellido, email, password, password_confirmation, Rol.USUARIO);

        return "redirect:/login?ok=alta exitosa";
    }

}
