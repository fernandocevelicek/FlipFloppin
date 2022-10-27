package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
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
    public String index(ModelMap modelo) {
        try {
            if (session.getAttribute("usuario_session") != null) {
                Usuario user = (Usuario) session.getAttribute("usuario_session");
                modelo.put("username", user.getNombre());
                modelo.addAttribute("user_rol", user.getRol());
            }

            return "index.html";
        } catch (Exception e) {
            e.printStackTrace();
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/producto/listado")
    public String listadoProductos(ModelMap modelo) {
        try {
            List<ProductoDTO> productos = productoService.findAll();
            System.out.println(productos.size());
            System.out.println(productos.get(0).getImagenes().get(0));
            modelo.addAttribute("productos", productos);

            return "listadoProducto.html";
        } catch (ProductoException e) {
            e.printStackTrace();
            modelo.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
