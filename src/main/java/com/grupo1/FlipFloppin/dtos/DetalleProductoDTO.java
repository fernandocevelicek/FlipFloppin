package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.Color;
import com.grupo1.FlipFloppin.enums.TallePrenda;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"producto"})
public class DetalleProductoDTO {

    private Long id;
    private Integer stock;

    private TallePrenda tallePrenda;
    private Integer talleCalzado;
    private Color color;
    private ProductoDTO producto;

}
