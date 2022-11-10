package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.PedidoDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.services.PedidoService;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public String listadoPedidosUsuario(Model model){
        try{
            Usuario usuario = (Usuario) session.getAttribute("usuario_session");
            List<PedidoDTO> pedidos = usuarioService.findById(usuario.getId()).getPedidos();
            model.addAttribute("pedidos", pedidos);
            return "listado_pedidos_usuario";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
