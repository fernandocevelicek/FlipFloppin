package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import lombok.Data;

@Data
public class PedidoDTO {

    private Long id;
    private EstadoDomicilio estado;
    private DomicilioDTO ubicacionEntrega;
    private Double total;
    private UsuarioDTO usuario;

}
