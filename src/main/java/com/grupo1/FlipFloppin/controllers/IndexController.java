package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String index(ModelMap model) {
        try {
            if (session.getAttribute("usuario_session") != null) {
                Usuario user = (Usuario) session.getAttribute("usuario_session");
                model.put("username", user.getNombre());
                model.addAttribute("user_rol", user.getRol());
            }
            // DESPUES HAY QUE CAMBIARLO POR LOS ULTIMOS 5 //
            List<ProductoDTO> productos = productoService.getLastFive();
            model.addAttribute("productos", productos);
            return "index.html";
        } catch (ProductoException e) {
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

}
