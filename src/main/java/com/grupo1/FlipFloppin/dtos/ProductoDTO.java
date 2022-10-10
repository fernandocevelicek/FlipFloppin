package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductoDTO {

    private Long id;
    private List<DetalleProductoDTO> detalle;
    private Double precio;
    private Integer stock;
    private String descripcion;
    private EstadoDomicilio estado;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
