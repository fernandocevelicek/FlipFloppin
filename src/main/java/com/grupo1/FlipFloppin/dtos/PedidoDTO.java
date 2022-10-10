package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import lombok.Data;

import java.util.Date;

@Data
public class PedidoDTO {

    private Long id;
    private EstadoDomicilio estado;
    private DomicilioDTO ubicacionEntrega;
    private Double total;
    private UsuarioDTO usuario;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
