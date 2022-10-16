package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
import lombok.Data;

import java.util.Date;
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
    private Rol rol;
    private List<DomicilioDTO> ubicacionesEnvio;
    private CarritoDTO carrito;
    private List<PedidoDTO> pedidos;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
