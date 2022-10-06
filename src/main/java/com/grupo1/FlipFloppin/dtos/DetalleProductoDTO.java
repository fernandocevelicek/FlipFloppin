package com.grupo1.FlipFloppin.dtos;

import lombok.Data;

@Data
public class DetalleProductoDTO {

    private Long id;
    private String marca;
    private String talle;
    private String color;
    private ProductoDTO producto;

}
