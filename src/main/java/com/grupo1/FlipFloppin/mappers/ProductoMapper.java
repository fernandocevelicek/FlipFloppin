package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    static ProductoMapper getInstance() {
        return Mappers.getMapper(ProductoMapper.class);
    }

    @Mapping(source = "id", target = "id")
    ProductoDTO toDTO(Producto source);

    @Mapping(source = "id", target = "id")
    Producto toEntity(ProductoDTO source);

    @Mapping(source = "id", target = "id")
    List<ProductoDTO> toDTOsList(List<Producto> source);

    @Mapping(source = "id", target = "id")
    List<Producto> toEntitiesList(List<ProductoDTO> source);

}
