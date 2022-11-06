package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.services.CarritoService;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar_producto")
    public String agregarProducto(Model model, @RequestParam("idProducto") Long idProducto, @RequestParam(value = "idDetalle") Long idDetalle, @RequestParam(value = "idUsuario") Long idUsuario, @RequestParam(value = "cantidad") Integer cantidad){
        try{
            // - si usuario no tiene carrito crear uno nuevo
            // - crear productoCompra
            // - agregar productoCompra a carrito
            // - persistir carrito con nuevo producto.

            return "redirect:/producto/filtro";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


}
