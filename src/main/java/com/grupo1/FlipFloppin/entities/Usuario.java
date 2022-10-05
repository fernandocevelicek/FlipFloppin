package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.entities.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuarios")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    private String fotoPerfil;

    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado",nullable = false)
    private Estado estado;
    @OneToMany(mappedBy = "usuarioD",cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_ubicacionesEnvio")
    private List<Domicilio> ubicacionesEnvio;

    @OneToOne(mappedBy = "usuario")
    @JoinColumn(name = "fk_carrito")
    private Carrito carrito;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_pedido")
    private List<Pedido> pedidos;

}
