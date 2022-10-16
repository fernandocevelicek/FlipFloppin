package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.PedidoDTO;
import com.grupo1.FlipFloppin.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    static PedidoMapper getInstance() {
        return Mappers.getMapper(PedidoMapper.class);
    }

    @Mapping(source = "id", target = "id")
    PedidoDTO toDTO(Pedido source);

    @Mapping(source = "id", target = "id")
    Pedido toEntity(PedidoDTO source);

    @Mapping(source = "id", target = "id")
    List<PedidoDTO> toDTOsList(List<Pedido> source);

    @Mapping(source = "id", target = "id")
    List<Pedido> toEntitiesList(List<PedidoDTO> source);

}
