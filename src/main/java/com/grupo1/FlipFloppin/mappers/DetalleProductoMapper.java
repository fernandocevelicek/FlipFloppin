package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.entities.DetalleProducto;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleProductoMapper {

    static DetalleProductoMapper getInstance() {
        return Mappers.getMapper(DetalleProductoMapper.class);
    }

    @InheritInverseConfiguration
    DetalleProductoDTO toDTO(DetalleProducto source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    DetalleProducto toEntity(DetalleProductoDTO source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    List<DetalleProductoDTO> toDTOsList(List<DetalleProducto> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    List<DetalleProducto> toEntitiesList(List<DetalleProductoDTO> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default DetalleProductoDTO toDTO(DetalleProducto source) {
        return toDTO(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default DetalleProducto toEntity(DetalleProductoDTO source) {
        return toEntity(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<DetalleProductoDTO> toDTOsList(List<DetalleProducto> source) {
        return toDTOsList(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<DetalleProducto> toEntitiesList(List<DetalleProductoDTO> source) {
        return toEntitiesList(source, new CycleAvoidingMappingContext());
    }

}
