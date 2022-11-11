package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.PedidoDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.exceptions.PedidoException;
import com.grupo1.FlipFloppin.services.PedidoService;
import com.grupo1.FlipFloppin.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@PreAuthorize("hasRole('ROLE_USUARIO')||hasRole('ROLE_ADMINISTRADOR')")
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
    public String listadoPedidosUsuario(Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario_session");
        List<PedidoDTO> pedidos = usuarioService.findById(usuario.getId()).getPedidos();
        model.addAttribute("pedidos", pedidos);
        return "listado_pedidos_usuario";
    }

    @PostMapping("/cancelar_pedido/{id}")
    public String cancelarPedido(Model model, @PathVariable("id") Long idPedido) {
        try {
            pedidoService.cancelarPedido(idPedido);
            return "redirect:/pedido";
        } catch (PedidoException e) {
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

}
