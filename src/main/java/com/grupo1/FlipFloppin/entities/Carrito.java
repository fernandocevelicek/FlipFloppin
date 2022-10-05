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
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST} )
    @JoinTable(name = "carrito_producto",joinColumns = @JoinColumn(name = "fk_carrito"),inverseJoinColumns = @JoinColumn(name = "fk_producto"))
    private List<ProductoCompra> productos;

    private Double total;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

}