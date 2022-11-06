package com.grupo1.FlipFloppin.dtos.producto;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class ProductoDTO {

    private Long id;
    private String nombre;
    private String marca;
    private List<String> imagenes;
    private Categoria categoria;
    private Sexo sexo;
    private List<DetalleProductoDTO> detalle;
    private Double precio;
    private String descripcion;
    private EstadoProducto estado;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
