package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.exceptions.UsuarioException;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@Controller
@RequestMapping("/usuario")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/abm_usuarios")
    public String getAll(Model model) {
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "abm_usuarios";
    }

    @GetMapping("/formulario/{id}")
    public String formularioUsuario(Model model, @PathVariable("id") long id) {
        model.addAttribute("roles", Rol.values());
        model.addAttribute("estados", EstadoUsuario.values());
        if (id == 0) {
            model.addAttribute("usuario", new UsuarioDTO());
        } else {
            model.addAttribute("usuario", usuarioService.findById(id));
        }
        return "formulario_usuario";
    }

    @PostMapping("/formulario/{id}")
    public String postFormularioUsuario(ModelMap modelo,
                                        @RequestParam(required = true) Long id,
                                        @RequestParam(required = true) String nombre,
                                        @RequestParam(required = true) String apellido,
                                        @RequestParam(required = true) String password,
                                        @RequestParam(required = true) EstadoUsuario estado,
                                        @RequestParam(required = true) String email,
                                        @RequestParam(required = true) String password_confirmation) {
        try {
            if (id == 0) {
                usuarioService.altaUsuarioCompleto(nombre, apellido, email, password, password_confirmation, estado);
            } else {
                usuarioService.modificarUsuarioCompleto(id, nombre, apellido, email, password, password_confirmation, estado);
            }
        } catch (UsuarioException e) {
            return "redirect:/usuario/abm_usuarios?error=" + e.getMessage();
        }

        return "redirect:/usuario/abm_usuarios";
    }

    @PostMapping("/baja_usuario/{id}")
    public String bajarUsuario(Model model, @PathVariable("id") long id) {
        try {
            this.usuarioService.deleteById(id);
            return "redirect:/usuario/abm_usuarios";
        } catch (UsuarioException e) {
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }
}
