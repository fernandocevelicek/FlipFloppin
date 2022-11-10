package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.entities.ProductoCompra;
import com.grupo1.FlipFloppin.enums.EstadoPedido;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PedidoDTO {

    private Long id;
    private EstadoPedido estado;
    private DomicilioDTO ubicacionEntrega;
    private Double total;
    private List<ProductoCompraDTO> productos;
    private UsuarioDTO usuario;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
