package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.grupo1.FlipFloppin.utils.Constants.BASE_VIEW_PATH;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/abm_productos")
    public String getAll(Model model) {
        try {
            List<ProductoDTO> productos = productoService.findAll();
            model.addAttribute("productos", productos);
            return BASE_VIEW_PATH + "abm_productos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/{id}")
    public String formularioProducto(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("estados", EstadoProducto.values());
            if (id == 0) {
                model.addAttribute("producto", new ProductoDTO());
            } else {
                model.addAttribute("producto", productoService.findById(id));
            }
            return BASE_VIEW_PATH + "formulario_producto";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/{id}")
    public String postFormularioProducto(ModelMap modelo,
                                         @PathVariable Long id,
                                         @ModelAttribute ProductoDTO productoDTO) throws Exception {

        if (productoDTO.getId() == 0) {
            productoService.save(productoDTO);
        } else {
            productoService.update(productoDTO, id);
        }

        return "redirect:/producto/abm_productos";
    }

    @PostMapping("/baja_producto/{id}")
    public String bajarProducto(Model model, @PathVariable("id") long id) {
        try {
            productoService.deleteById(id);
            return "redirect:/producto/abm_productos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
