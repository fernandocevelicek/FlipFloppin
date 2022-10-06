package com.grupo1.FlipFloppin.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CarritoDTO {

    private Long id;
    private List<ProductoCompraDTO> productos;
    private Double total;
    private UsuarioDTO usuario;

}