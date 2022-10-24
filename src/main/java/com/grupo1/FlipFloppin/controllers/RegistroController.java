package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.exceptions.UsuarioException;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.grupo1.FlipFloppin.utils.Constants.BASE_VIEW_PATH;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro_cliente")
    public String registroCliente(ModelMap modelo){
        return BASE_VIEW_PATH + "registro_cliente.html";
    }

    @PostMapping("/registrar_cliente")
    public String registrarCliente(ModelMap modelo,
                                   @RequestParam() String nombre,
                                   @RequestParam() String apellido,
                                   @RequestParam() String password,
                                   @RequestParam() String email,
                                   @RequestParam() String password_confirmation) {

        try {
            usuarioService.registrarUsuario(nombre, apellido, email, password, password_confirmation, Rol.USUARIO);
            return "redirect:/login?ok=alta exitosa";
        } catch (UsuarioException e) {
            return "redirect:/login?error="+e.getMessage();
        }
    }

}
