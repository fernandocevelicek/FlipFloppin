package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import com.grupo1.FlipFloppin.enums.Provincia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOMICILIO")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String piso;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "provincia",nullable = false)
    private Provincia provincia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado",nullable = false)
    private EstadoDomicilio estado;

    @ManyToOne()
    private Usuario usuarioDir;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

}
