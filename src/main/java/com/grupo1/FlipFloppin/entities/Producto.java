package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.EstadoProducto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTO")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    private List<DetalleProducto> detalle;

    private Double precio;

    private Integer stock;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name="estado", nullable = false)
    private EstadoProducto estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

}
