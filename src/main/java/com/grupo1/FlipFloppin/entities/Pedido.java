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
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private enum estado{PENDIENTE,COMPLETADO,ERROR}

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_domicilio")
    private Domicilio ubicacionEntrega;

    private Double total;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
}
