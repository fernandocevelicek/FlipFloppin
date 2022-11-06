package com.grupo1.FlipFloppin.dtos.producto;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoIndividualDTO {

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
    private DetalleProductoDTO detalleActual;

    public ProductoIndividualDTO(ProductoDTO source) {
        id = source.getId();
        nombre = source.getNombre();
        marca = source.getMarca();
        imagenes = source.getImagenes();
        categoria = source.getCategoria();
        sexo = source.getSexo();
        detalle = source.getDetalle();
        precio = source.getPrecio();
        descripcion = source.getDescripcion();
        estado = source.getEstado();
        fechaAlta = source.getFechaAlta();
        fechaModificacion = source.getFechaModificacion();
        fechaBaja = source.getFechaBaja();
    }

}
