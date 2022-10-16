package com.grupo1.FlipFloppin.controllers.rest;

import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/bajaProducto{id}")
    public String bajarProducto(Model model, @PathVariable("id") long id) {

        try {
            this.productoService.deleteById(id);
            return "redirect:/.....";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
