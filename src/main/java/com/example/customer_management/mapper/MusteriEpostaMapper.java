package com.example.customer_management.mapper;

import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MusteriEpostaMapper {

    MusteriEpostaDTO toDTO(MusteriEposta entity);

    MusteriEposta toEntity(MusteriEpostaDTO dto);
}
