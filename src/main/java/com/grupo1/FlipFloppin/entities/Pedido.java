package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.EstadoPedido;
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
@Table(name = "PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado",nullable = false)
    private EstadoPedido estado;

    @ManyToOne()
    @JoinColumn(name = "fk_domicilio")
    private Domicilio ubicacionEntrega;

    private Double total;

    @ManyToMany()
    @JoinTable(name = "pedido_producto_compra",joinColumns = @JoinColumn(name = "fk_pedido"),inverseJoinColumns = @JoinColumn(name = "fk_producto_compra"))
    private List<ProductoCompra> productos;

    @ManyToOne()
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

}
