package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import com.grupo1.FlipFloppin.enums.Provincia;
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
    private Provincia provincias;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado",nullable = false)
    private EstadoDomicilio estado;

    @ManyToOne()
    private Usuario usuarioDir;

}
