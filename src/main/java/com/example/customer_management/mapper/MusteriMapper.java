package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MusteriAdresMapper.class})
public interface MusteriMapper {

    @Mapping(target = "adresIds", source = "adresler", qualifiedByName = "adresToIds")
    MusteriDTO toDTO(Musteri entity);

    @Mapping(target = "adresler", source = "adresIds", qualifiedByName = "idsToAdres")
    Musteri toEntity(MusteriDTO dto);

    @Named("adresToIds")
    default List<Long> adresToIds(List<MusteriAdres> adresler) {
        if (adresler == null) return new ArrayList<>();
        return adresler.stream()
                .map(MusteriAdres::getId)
                .collect(Collectors.toList());
    }

    @Named("idsToAdres")
    default List<MusteriAdres> idsToAdres(List<Long> ids) {
        // Just return empty list,
        // actual loading from repository should happen in service
        return new ArrayList<>();
    }
}

