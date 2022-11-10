package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.PedidoDTO;
import com.grupo1.FlipFloppin.entities.Pedido;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    static PedidoMapper getInstance() {
        return Mappers.getMapper(PedidoMapper.class);
    }

    @InheritInverseConfiguration
    PedidoDTO toDTO(Pedido source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    Pedido toEntity(PedidoDTO source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    List<PedidoDTO> toDTOsList(List<Pedido> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    List<Pedido> toEntitiesList(List<PedidoDTO> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default PedidoDTO toDTO(Pedido source) {
        return toDTO(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Pedido toEntity(PedidoDTO source) {
        return toEntity(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<PedidoDTO> toDTOsList(List<Pedido> source) {
        return toDTOsList(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Pedido> toEntitiesList(List<PedidoDTO> source) {
        return toEntitiesList(source, new CycleAvoidingMappingContext());
    }

}
