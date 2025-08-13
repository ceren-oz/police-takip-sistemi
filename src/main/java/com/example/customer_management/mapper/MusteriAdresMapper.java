package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MusteriMapper.class})
public interface MusteriAdresMapper {


    /*MusteriAdresDTO toDTO(MusteriAdres entity);

    MusteriAdres toEntity(MusteriAdresDTO dto);*/

    @Mapping(source = "musteriler", target = "musteriIds", qualifiedByName = "musteriListToIds")
    MusteriAdresDTO toDTO(MusteriAdres entity);

    @Mapping(source = "musteriIds", target = "musteriler", qualifiedByName = "idsToMusteriList")
    MusteriAdres toEntity(MusteriAdresDTO dto);


    @Named("musteriListToIds")
    static List<String> musteriListToIds(List<Musteri> musteriler) {
        if (musteriler == null) return null;
        return musteriler.stream()
                .map(Musteri::getId)
                .collect(Collectors.toList());
    }

    @Named("idsToMusteriList")
    static List<Musteri> idsToMusteriList(List<String> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> {
                    Musteri musteri = new Musteri();
                    musteri.setId(id);
                    return musteri;
                })
                .collect(Collectors.toList());
    }
}
