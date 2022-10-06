package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String fotoPerfil;
    private String email;
    private String password;
    private EstadoUsuario estado;
    private List<DomicilioDTO> ubicacionesEnvio;
    private CarritoDTO carrito;
    private List<PedidoDTO> pedidos;

}
