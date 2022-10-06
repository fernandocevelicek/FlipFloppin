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
@Table(name = "CARRITO")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(name = "carrito_producto_compra",joinColumns = @JoinColumn(name = "fk_carrito"),inverseJoinColumns = @JoinColumn(name = "fk_producto_compra"))
    private List<ProductoCompra> productos;

    private Double total;

    @OneToOne()
    private Usuario usuario;

}