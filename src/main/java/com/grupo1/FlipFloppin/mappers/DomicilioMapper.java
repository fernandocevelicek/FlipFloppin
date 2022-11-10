package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.DomicilioDTO;
import com.grupo1.FlipFloppin.entities.Domicilio;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomicilioMapper {

    static DomicilioMapper getInstance() { return Mappers.getMapper(DomicilioMapper.class); }

    @InheritInverseConfiguration
    DomicilioDTO toDTO(Domicilio source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    Domicilio toEntity(DomicilioDTO source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    List<DomicilioDTO> toDTOsList(List<Domicilio> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    List<Domicilio> toEntitiesList(List<DomicilioDTO> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default DomicilioDTO toDTO(Domicilio source) {
        return toDTO(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Domicilio toEntity(DomicilioDTO source) {
        return toEntity(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<DomicilioDTO> toDTOsList(List<Domicilio> source) {
        return toDTOsList(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Domicilio> toEntitiesList(List<DomicilioDTO> source) {
        return toEntitiesList(source, new CycleAvoidingMappingContext());
    }

}
