package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.entities.DetalleProducto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleProductoMapper {

    static DetalleProductoMapper getInstance() {
        return Mappers.getMapper(DetalleProductoMapper.class);
    }

    @Mapping(source = "id", target = "id")
    DetalleProductoDTO toDTO(DetalleProducto source);

    @Mapping(source = "id", target = "id")
    DetalleProducto toEntity(DetalleProductoDTO source);

    @Mapping(source = "id", target = "id")
    List<DetalleProductoDTO> toDTOsList(List<DetalleProducto> source);

    @Mapping(source = "id", target = "id")
    List<DetalleProducto> toEntitiesList(List<DetalleProductoDTO> source);

}
