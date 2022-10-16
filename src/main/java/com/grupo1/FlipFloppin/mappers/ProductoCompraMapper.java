package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.ProductoCompraDTO;
import com.grupo1.FlipFloppin.entities.ProductoCompra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoCompraMapper {

    static ProductoCompraMapper getInstance() {
        return Mappers.getMapper(ProductoCompraMapper.class);
    }

    @Mapping(source = "id", target = "id")
    ProductoCompraDTO toDTO(ProductoCompra source);

    @Mapping(source = "id", target = "id")
    ProductoCompra toEntity(ProductoCompraDTO source);

    @Mapping(source = "id", target = "id")
    List<ProductoCompraDTO> toDTOsList(List<ProductoCompra> source);

    @Mapping(source = "id", target = "id")
    List<ProductoCompra> toEntitiesList(List<ProductoCompraDTO> source);

}
