package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    static UsuarioMapper getInstance() {
        return Mappers.getMapper(UsuarioMapper.class);
    }

    @InheritInverseConfiguration
    UsuarioDTO toDTO(Usuario source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    Usuario toEntity(UsuarioDTO source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    List<UsuarioDTO> toDTOsList(List<Usuario> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    List<Usuario> toEntitiesList(List<UsuarioDTO> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default UsuarioDTO toDTO(Usuario source) {
        return toDTO(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Usuario toEntity(UsuarioDTO source) {
        return toEntity(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<UsuarioDTO> toDTOsList(List<Usuario> source) {
        return toDTOsList(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Usuario> toEntitiesList(List<UsuarioDTO> source) {
        return toEntitiesList(source, new CycleAvoidingMappingContext());
    }

}
