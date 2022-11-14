package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.exceptions.UsuarioException;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/abm_usuarios/{nro}")
    public String getAll(Model model,@PathVariable(value = "nro") int pageNo) {
        try {
            int pageSize = 10;
            Page<Usuario> page = usuarioService.findAllPaginated(pageNo, pageSize);
            List <Usuario> users = page.getContent();
            model.addAttribute("currentPage", pageNo);
            if(pageNo>0){
                model.addAttribute("previousPage",pageNo-1);
            }
            if(pageNo+1<page.getTotalPages()){
                model.addAttribute("nextPage",pageNo+1);
            }
            model.addAttribute("totalPages", page.getTotalPages()-1);
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("usuarios", users);
            return "abm_usuarios";
        }catch (Exception e){
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }

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
            return "redirect:/usuario/abm_usuarios/0?error=" + e.getMessage();
        }

        return "redirect:/usuario/abm_usuarios/0";
    }

    @PostMapping("/baja_usuario/{id}")
    public String bajarUsuario(Model model, @PathVariable("id") long id) {
        try {
            this.usuarioService.deleteById(id);
            return "redirect:/usuario/abm_usuarios/0";
        } catch (UsuarioException e) {
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }
}
