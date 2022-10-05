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
@Table(name = "detalle_productos")
public class DetalleProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private enum categoria{PRENDA_SUPERIOR,PRENDA_INFERIOR,CALZADO}
    /*Como pa poner algo*/

    private enum marca{RIP_CURL, DC, VANS, FLIP_FLOPPIN, BILLABONG}

    private String talle;

    private String color;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Producto producto;


}
