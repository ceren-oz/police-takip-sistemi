package com.example.customer_management.mapper;

import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import org.mapstruct.Mapper;
import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriEposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MusteriMapper.class})
public interface MusteriEpostaMapper {

    @Mapping(source = "musteri.id", target = "musteriId")
    MusteriEpostaDTO toDTO(MusteriEposta entity);

    @Mapping(source = "musteriId", target = "musteri.id")
    MusteriEposta toEntity(MusteriEpostaDTO dto);

    default String mapMusteriToId(Musteri musteri){
        return musteri != null ? musteri.getId() : null;
    }

    default Musteri mapIdToMusteri(String id){
        if(id == null) return null;
        Musteri musteri = new Musteri();
        musteri.setId(id);
        return musteri;
    }
}
