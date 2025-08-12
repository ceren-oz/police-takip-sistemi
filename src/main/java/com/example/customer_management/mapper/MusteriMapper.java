package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MusteriAdresMapper.class, MusteriEpostaMapper.class})
public interface MusteriMapper {


    MusteriDTO toDTO(Musteri entity);

    Musteri toEntity(MusteriDTO dto);

}

