package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.entities.Carrito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarritoMapper {

    static CarritoMapper getInstance() {
        return Mappers.getMapper(CarritoMapper.class);
    }

    @Mapping(source = "id", target = "id")
    CarritoDTO toDTO(Carrito source);

    @Mapping(source = "id", target = "id")
    Carrito toEntity(CarritoDTO source);

    @Mapping(source = "id", target = "id")
    List<CarritoDTO> toDTOsList(List<Carrito> source);

    @Mapping(source = "id", target = "id")
    List<Carrito> toEntitiesList(List<CarritoDTO> source);

}
