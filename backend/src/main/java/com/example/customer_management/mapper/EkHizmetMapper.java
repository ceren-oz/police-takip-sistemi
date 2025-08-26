package com.example.customer_management.mapper;

import com.example.customer_management.domain.EkHizmet;
import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.Police;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EkHizmetMapper {

    @Mapping(source = "policeler", target = "policeIds", qualifiedByName = "mapPoliceToIds")
    EkHizmetDTO toDTO(EkHizmet entity);

    @Mapping(source = "policeIds", target = "policeler", qualifiedByName = "mapIdsToPolice")
    EkHizmet toEntity(EkHizmetDTO entity);

    @Named("mapPoliceToIds")
    default Set<Long> mapPoliceToIds(Set<Police> policeler) {
        if (policeler == null) return null;
        return policeler.stream().map(Police::getId).collect(Collectors.toSet());
    }

    @Named("mapIdsToPolice")
    default Set<Police> mapIdsToPolice(Set<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Police e = new Police();
            e.setId(id);
            return e;
        }).collect(Collectors.toSet());
    }
}
