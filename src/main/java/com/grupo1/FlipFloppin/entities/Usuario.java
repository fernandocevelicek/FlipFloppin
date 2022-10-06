package com.grupo1.FlipFloppin.entities;

import com.grupo1.FlipFloppin.enums.EstadoUsuario;
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
@Table(name = "USUARIO")
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
    private EstadoUsuario estado;

    @OneToMany(mappedBy = "usuarioDir")
    private List<Domicilio> ubicacionesEnvio;

    @OneToOne(mappedBy = "usuario")
    private Carrito carrito;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

}
