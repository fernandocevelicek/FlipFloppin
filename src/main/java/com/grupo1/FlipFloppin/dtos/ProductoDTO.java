package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoProducto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductoDTO {

    private Long id;
    private String nombre;
    private List<DetalleProductoDTO> detalle;
    private Double precio;
    private Integer stock;
    private String descripcion;
    private EstadoProducto estado;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
