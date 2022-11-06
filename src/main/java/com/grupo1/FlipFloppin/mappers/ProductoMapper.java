package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    static ProductoMapper getInstance() {
        return Mappers.getMapper(ProductoMapper.class);
    }

    @InheritInverseConfiguration
    ProductoDTO toDTO(Producto source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    Producto toEntity(ProductoDTO source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    List<ProductoDTO> toDTOsList(List<Producto> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @Mapping(source = "id", target = "id")
    List<Producto> toEntitiesList(List<ProductoDTO> source, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ProductoDTO toDTO(Producto source) {
        return toDTO(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Producto toEntity(ProductoDTO source) {
        return toEntity(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<ProductoDTO> toDTOsList(List<Producto> source) {
        return toDTOsList(source, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Producto> toEntitiesList(List<ProductoDTO> source) {
        return toEntitiesList(source, new CycleAvoidingMappingContext());
    }

}
