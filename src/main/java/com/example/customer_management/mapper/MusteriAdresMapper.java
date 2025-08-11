package com.example.customer_management.mapper;

import com.example.customer_management.domain.MusteriAdres;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MusteriAdresMapper {

    MusteriAdresDTO toDTO(MusteriAdres entity);

    MusteriAdres toEntity(MusteriAdresDTO dto);
}
