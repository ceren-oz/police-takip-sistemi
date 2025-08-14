package com.example.customer_management.mapper;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MusteriAdresMapper.class, MusteriEpostaMapper.class})
public interface MusteriMapper {


   /* MusteriDTO toDTO(Musteri entity);

    Musteri toEntity(MusteriDTO dto);*/

    @Mapping(source = "adresler", target = "adresIds", qualifiedByName = "adresListToIds")
    @Mapping(source = "epostalar", target = "epostaIds", qualifiedByName = "epostaListToIds")
    MusteriDTO toDTO(Musteri entity);

    @Mapping(source = "adresIds", target = "adresler", qualifiedByName = "idsToAdresList")
    @Mapping(source = "epostaIds", target = "epostalar", qualifiedByName = "idsToEpostaList")
    Musteri toEntity(MusteriDTO dto);

    @Named("adresListToIds")
    static List<Long> adresListToIds(List<MusteriAdres> adresler) {
        if (adresler == null) return null;
        return adresler.stream()
                .map(MusteriAdres::getId)
                .collect(Collectors.toList());
    }

    @Named("epostaListToIds")
    static List<Long> epostaListToIds(List<MusteriEposta> epostalar) {
        if (epostalar == null) return null;
        return epostalar.stream()
                .map(MusteriEposta::getId)
                .collect(Collectors.toList());
    }

    @Named("idsToAdresList")
    static List<MusteriAdres> idsToAdresList(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> {
                    MusteriAdres adres = new MusteriAdres();
                    adres.setId(id);
                    return adres;
                })
                .collect(Collectors.toList());
    }

    @Named("idsToEpostaList")
    static List<MusteriEposta> idsToEpostaList(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> {
                    MusteriEposta eposta = new MusteriEposta();
                    eposta.setId(id);
                    return eposta;
                })
                .collect(Collectors.toList());
    }

}

