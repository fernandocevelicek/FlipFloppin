package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.Color;
import com.grupo1.FlipFloppin.enums.TallePrenda;
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
@Table(name = "DETALLE_PRODUCTO")
public class DetalleProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private TallePrenda tallePrenda;

    private Integer talleCalzado;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne()
    private Producto producto;

}
