package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.exceptions.CarritoException;
import com.grupo1.FlipFloppin.exceptions.PedidoException;
import com.grupo1.FlipFloppin.exceptions.ProductoCompraException;
import com.grupo1.FlipFloppin.services.CarritoService;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@PreAuthorize("hasRole('ROLE_USUARIO')||hasRole('ROLE_ADMINISTRADOR')")
@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private HttpSession session;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public String carrito(Model model){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            CarritoDTO carritoDTO = carritoService.findByUsuarioId(usuario.getId());
            model.addAttribute("carrito", carritoDTO);
            return "carrito";
        }catch (CarritoException e){
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/agregar_producto")
    public String agregarProducto(Model model, @RequestParam("idProducto") Long idProducto, @RequestParam(value = "idDetalle") Long idDetalle, @RequestParam(value = "cantidad") Integer cantidad, @RequestParam(value = "sourceURL") String sourceURL, HttpServletRequest request, HttpServletResponse response){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            if (usuario == null) {
                RequestCache requestCache = new HttpSessionRequestCache();
                requestCache.saveRequest(request,response);
                return "redirect:/login";
            }
            carritoService.agregarProducto(idProducto, idDetalle, usuario.getId(), cantidad);
            return "redirect:"+sourceURL;
        }catch (ProductoCompraException | CarritoException e){
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/quitar_producto")
    public String quitarProducto(Model model, @RequestParam("idProducto") Long idProducto, @RequestParam(value = "idDetalle") Long idDetalle, @RequestParam(value = "cantidad") Integer cantidad, HttpServletRequest request, HttpServletResponse response){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            carritoService.quitarProducto(idProducto, idDetalle, usuario.getId(), cantidad);
            return "redirect:/carrito";
        }catch (ProductoCompraException e){
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/confirmar_compra")
    public String confirmarCompra(Model model){
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            UsuarioDTO usuarioDTO = usuarioService.findById(usuario.getId());
            model.addAttribute("domicilios", usuarioDTO.getUbicacionesEnvio());
            return "confirmar_compra";
    }

    @PostMapping("/confirmar_compra")
    public String confirmaCompra(Model model, @RequestParam("idDomicilio") Long idDomicilio){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            carritoService.confirmarCompra(usuario.getId(), idDomicilio);
            return "redirect:/producto/listado";
        }catch (PedidoException e){
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

}
