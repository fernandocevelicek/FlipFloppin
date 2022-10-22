package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.Color;
import com.grupo1.FlipFloppin.enums.TallePrenda;
import lombok.Data;

import javax.persistence.*;

@Data
public class DetalleProductoDTO {

    private Long id;
    private Integer stock;
    private TallePrenda tallePrenda;
    private Integer talleCalzado;
    private Color color;
    private Producto producto;

}
