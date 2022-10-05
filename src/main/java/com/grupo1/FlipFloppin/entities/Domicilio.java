package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.entities.enums.Estado;
import com.grupo1.FlipFloppin.entities.enums.Provincias;
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
@Table(name = "domicilios")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String piso;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "provincia",nullable = false)
    private Provincias provincias;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado",nullable = false)
    private Estado estado;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuarioDir;

    @OneToMany(mappedBy = "domicilio")
    @JoinColumn(name = "fk_pedido")
    private List<Pedido> pedidos;
}
