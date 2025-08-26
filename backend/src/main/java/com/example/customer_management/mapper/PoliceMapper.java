package com.example.customer_management.mapper;

import com.example.customer_management.domain.EkHizmet;
import com.example.customer_management.domain.Police;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PoliceMapper {

    @Mapping(source = "musteri.id", target = "musteriId")
    /*@Mapping(source = "arac.aracId", target = "aracId")*/
    @Mapping(source = "ekHizmetler", target = "ekHizmetlerIds", qualifiedByName = "mapEkHizmetToIds")
    /*@Mapping(source = "teminatlar", target = "teminatlarIds", qualifiedByName = "mapTeminatToIds")*/
    PoliceDTO toDto(Police police);

    @Mapping(source = "musteriId", target = "musteri.id")
    /*@Mapping(source = "aracId", target = "arac.aracId")*/
    @Mapping(source = "ekHizmetlerIds", target = "ekHizmetler", qualifiedByName = "mapIdsToEkHizmet")
    /*@Mapping(source = "teminatlarIds", target = "teminatlar", qualifiedByName = "mapIdsToTeminat")*/
    Police toEntity(PoliceDTO dto);

    @Named("mapEkHizmetToIds")
    default Set<Long> mapEkHizmetToIds(Set<EkHizmet> ekHizmetler) {
        if (ekHizmetler == null) return null;
        return ekHizmetler.stream().map(EkHizmet::getId).collect(Collectors.toSet());
    }

    @Named("mapIdsToEkHizmet")
    default Set<EkHizmet> mapIdsToEkHizmet(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            EkHizmet e = new EkHizmet();
            e.setId(id);
            return e;
        }).collect(Collectors.toSet());
    }

  /*  @Named("mapTeminatToIds")
    default Set<Long> mapTeminatToIds(Set<Teminat> teminatlar) {
        if (teminatlar == null) return null;
        return teminatlar.stream().map(Teminat::getId).collect(Collectors.toSet());
    }

    @Named("mapIdsToTeminat")
    default Set<Teminat> mapIdsToTeminat(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Teminat t = new Teminat();
            t.setId(id);
            return t;
        }).collect(Collectors.toSet());
    }*/
}
