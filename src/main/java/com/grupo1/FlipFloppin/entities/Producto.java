package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
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

    private String marca;

    @ElementCollection
    @CollectionTable(name = "producto_imagen", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "imagen")
    private List<String> imagenes;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    private List<DetalleProducto> detalle;

    private Double precio;

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
