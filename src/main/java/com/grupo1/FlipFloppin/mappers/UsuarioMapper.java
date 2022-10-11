package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    static UsuarioMapper getInstance() {
        return Mappers.getMapper(UsuarioMapper.class);
    }

    @Mapping(source = "id", target = "id")
    UsuarioDTO toDTO(Usuario source);

    @Mapping(source = "id", target = "id")
    Usuario toEntity(UsuarioDTO source);

    @Mapping(source = "id", target = "id")
    List<UsuarioDTO> toDTOsList(List<Usuario> source);

    @Mapping(source = "id", target = "id")
    List<Usuario> toEntitiesList(List<UsuarioDTO> source);

}
