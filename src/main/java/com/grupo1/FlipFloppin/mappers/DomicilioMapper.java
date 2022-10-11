package com.grupo1.FlipFloppin.mappers;

import com.grupo1.FlipFloppin.dtos.DomicilioDTO;
import com.grupo1.FlipFloppin.entities.Domicilio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomicilioMapper {

    static DomicilioMapper getInstance() { return Mappers.getMapper(DomicilioMapper.class); }

    @Mapping(source = "id", target = "id")
    DomicilioDTO toDTO(Domicilio source);

    @Mapping(source = "id", target = "id")
    Domicilio toEntity(DomicilioDTO source);

    @Mapping(source = "id", target = "id")
    List<DomicilioDTO> toDTOsList(List<Domicilio> source);

    @Mapping(source = "id", target = "id")
    List<Domicilio> toEntitiesList(List<DomicilioDTO> source);

}
