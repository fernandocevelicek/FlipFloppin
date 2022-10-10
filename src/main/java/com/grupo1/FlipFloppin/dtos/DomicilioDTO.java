package com.grupo1.FlipFloppin.dtos;

import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import com.grupo1.FlipFloppin.enums.Provincia;
import lombok.Data;

import java.util.Date;

@Data
public class DomicilioDTO {

    private Long id;
    private String numero;
    private String piso;
    private String descripcion;
    private Provincia provincias;
    private EstadoDomicilio estado;
    private UsuarioDTO usuarioDir;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Date fechaBaja;

}
