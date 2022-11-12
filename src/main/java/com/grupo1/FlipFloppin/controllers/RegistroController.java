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

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro_cliente")
    public String registroCliente(ModelMap model, @RequestParam(required = false) String error){
        model.put("error", error);
        return "registro_cliente.html";
    }

    @PostMapping("/registrar_cliente")
    public String registrarCliente(ModelMap model,
                                   @RequestParam() String nombre,
                                   @RequestParam() String apellido,
                                   @RequestParam() String password,
                                   @RequestParam() String email,
                                   @RequestParam() String password_confirmation) {

        try {
            usuarioService.registrarUsuario(nombre, apellido, email, password, password_confirmation, Rol.USUARIO);
            return "redirect:/login?ok=alta exitosa";
        } catch (UsuarioException e) {
            System.out.println("########## entro al catch");
            return "redirect:/registro_cliente?error="+e.getMessage();
        }
    }

}
