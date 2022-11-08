package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.exceptions.CarritoException;
import com.grupo1.FlipFloppin.services.CarritoService;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("")
    public String carrito(Model model){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            CarritoDTO carritoDTO = carritoService.findByUsuarioId(usuario.getId());
            model.addAttribute("carrito", carritoDTO);
            return "carrito";
        }catch (CarritoException e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/agregar_producto")
    public String agregarProducto(Model model, @RequestParam("idProducto") Long idProducto, @RequestParam(value = "idDetalle") Long idDetalle, @RequestParam(value = "idUsuario") Long idUsuario, @RequestParam(value = "cantidad") Integer cantidad, @RequestParam(value = "sourceURL") String sourceURL){
        try{
            System.out.println(idProducto);
            System.out.println(idDetalle);
            System.out.println(idUsuario);
            System.out.println(cantidad);
            System.out.println(sourceURL);

            carritoService.agregarProducto(idProducto, idDetalle, idUsuario, cantidad);

            return "redirect:"+sourceURL;
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


}
