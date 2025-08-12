package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MusteriAdresMapper {

   // @Mapping(target = "musteriIds", source = "musteriler", qualifiedByName = "musterilerToIds")
    MusteriAdresDTO toDTO(MusteriAdres entity);

    MusteriAdres toEntity(MusteriAdresDTO dto);

   /* @Named("musterilerToIds")
    default List<String> musterilerToIds(List<Musteri> musteriler) {
        if (musteriler == null) return new ArrayList<>();
        return musteriler.stream().map(Musteri::getId).collect(Collectors.toList());
    }*/
}
