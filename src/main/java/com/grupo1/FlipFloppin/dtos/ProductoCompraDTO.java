package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import lombok.Data;

@Data
public class ProductoCompraDTO {

    private Long id;
    private Integer cantidad;
    private ProductoDTO producto;
    private Double subtotal;
    private Long idDetalle;

}
