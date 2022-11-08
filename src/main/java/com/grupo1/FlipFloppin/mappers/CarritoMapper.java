package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.entities.Carrito;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarritoMapper {

    static CarritoMapper getInstance() {
        return Mappers.getMapper(CarritoMapper.class);
    }

    @InheritInverseConfiguration
    CarritoDTO toDTO(Carrito source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    Carrito toEntity(CarritoDTO source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    List<CarritoDTO> toDTOsList(List<Carrito> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    List<Carrito> toEntitiesList(List<CarritoDTO> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default CarritoDTO toDTO(Carrito source) {
        return toDTO(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Carrito toEntity(CarritoDTO source) {
        return toEntity(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<CarritoDTO> toDTOsList(List<Carrito> source) {
        return toDTOsList(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Carrito> toEntitiesList(List<CarritoDTO> source) {
        return toEntitiesList(source, new CycleAvoidingMappingContext());
    }


}
