package com.grupo1.FlipFloppin.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTO_COMPRA")
public class ProductoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @OneToOne()
    private Producto producto;

    private Long idDetalle;

    private Double subtotal;

    public ProductoCompra(ProductoCompra productoCompra) {
        id = productoCompra.id;
        cantidad = productoCompra.cantidad;
        producto = productoCompra.producto;
        idDetalle = productoCompra.idDetalle;
        subtotal = productoCompra.subtotal;
    }

}
