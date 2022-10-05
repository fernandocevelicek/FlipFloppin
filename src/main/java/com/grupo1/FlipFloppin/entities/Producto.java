package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.entities.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_detalle")
    private List<DetalleProducto> detalle;

    private Double precio;

    private Integer stock;

    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name="estado", nullable = false)
    private Estado estado;
}
