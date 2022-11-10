package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.dtos.DomicilioDTO;
import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.exceptions.CarritoException;
import com.grupo1.FlipFloppin.mappers.UsuarioMapper;
import com.grupo1.FlipFloppin.services.CarritoService;
import com.grupo1.FlipFloppin.services.DomicilioService;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private HttpSession session;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioMapper usuarioMapper;

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
    public String agregarProducto(Model model, @RequestParam("idProducto") Long idProducto, @RequestParam(value = "idDetalle") Long idDetalle, @RequestParam(value = "cantidad") Integer cantidad, @RequestParam(value = "sourceURL") String sourceURL){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            carritoService.agregarProducto(idProducto, idDetalle, usuario.getId(), cantidad);
            return "redirect:"+sourceURL;
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/quitar_producto")
    public String quitarProducto(Model model, @RequestParam("idProducto") Long idProducto, @RequestParam(value = "idDetalle") Long idDetalle, @RequestParam(value = "cantidad") Integer cantidad){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            carritoService.quitarProducto(idProducto, idDetalle, usuario.getId(), cantidad);
            return "redirect:/carrito";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/confirmar_compra")
    public String confirmarCompra(Model model){
        try{
            UsuarioDTO usuario = usuarioMapper.toDTO((Usuario) session.getAttribute("usuario_session"));
            model.addAttribute("domicilios", usuario.getUbicacionesEnvio());
            return "confirmar_compra";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/confirmar_compra")
    public String confirmaCompra(Model model, @RequestParam("idDomicilio") Long idDomicilio){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            carritoService.confirmarCompra(usuario.getId(), idDomicilio);
            return "redirect:/producto/listado";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
