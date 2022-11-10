package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.DomicilioDTO;
import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import com.grupo1.FlipFloppin.enums.Provincia;
import com.grupo1.FlipFloppin.exceptions.DomicilioException;
import com.grupo1.FlipFloppin.services.DomicilioService;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/domicilio")
public class DomicilioController {

    @Autowired
    private HttpSession session;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/abm_domicilio")
    public String abmDomicilio(Model model) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            List<DomicilioDTO> domicilios = usuarioService.findById(usuario.getId()).getUbicacionesEnvio();

            model.addAttribute("domicilios", domicilios);
            model.addAttribute("estados", EstadoDomicilio.values());

            return "abm_domicilio_usuario";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/{id}")
    public String formulario(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("estados", EstadoDomicilio.values());
            model.addAttribute("provincias", Provincia.values());
            if (id == 0) {
                model.addAttribute("domicilioDTO", new DomicilioDTO());
            } else {
                DomicilioDTO domicilioDTO = domicilioService.findById(id);
                model.addAttribute("domicilioDTO", domicilioDTO);
            }
            return "formulario_domicilio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/{id}")
    public String postFormulario(ModelMap modelo,
                                 @PathVariable Long id,
                                 @ModelAttribute DomicilioDTO domicilioDTO) {
        Usuario usuario = (Usuario) session.getAttribute("usuario_session");
        UsuarioDTO usuarioDTO = usuarioService.findById(usuario.getId());
        domicilioDTO.setUsuarioDir(usuarioDTO);
        try {
            if (domicilioDTO.getId() == 0) {
                domicilioService.save(domicilioDTO);
            } else {
                domicilioService.update(domicilioDTO, id);
            }
            return "redirect:/domicilio/abm_domicilio";
        } catch (DomicilioException e) {
            return "redirect:/domicilio/abm_domicilio?error=" + e.getMessage();
        }
    }

    @PostMapping("/baja_domicilio/{id}")
    public String desactivarDomicilio(Model model, @PathVariable("id") long id) {
        try {
            domicilioService.deleteById(id);
            return "redirect:/domicilio/abm_domicilio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
