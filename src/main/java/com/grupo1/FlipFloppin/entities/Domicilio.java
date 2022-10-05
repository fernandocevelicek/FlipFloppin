package com.grupo1.FlipFloppin.entities;

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

    private enum provincia{CATAMARCA, CORRIENTES, CHACO, FORMOSA, JUJUY, MISIONES, TUCUMÁN, SALTA, SANTIAGO_DEL_ESTERO, LA_RIOJA, MENDOZA, SAN_JUAN, SAN_LUIS, CHUBUT, LA_PAMPA, NEUQUÉN, RÍO_NEGRO, SANTA_CRUZ,TIERRA_DEL_FUEGO,CÓRDOBA, ENTRE_RÍOS, SANTA_FE}

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuarioDir;

    @OneToMany(mappedBy = "domicilio")
    @JoinColumn(name = "fk_pedido")
    private List<Pedido> pedidos;
}
