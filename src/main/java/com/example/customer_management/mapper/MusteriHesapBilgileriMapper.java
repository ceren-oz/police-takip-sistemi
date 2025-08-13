package com.example.customer_management.mapper;
import com.example.customer_management.domain.Musteri; //Entity’de ManyToOne ilişki olduğu için toEntity metodunda set ediyoruz.
import com.example.customer_management.domain.MusteriHesapBilgileri; //Entity dönüşümünde kullanıyoruz.
import com.example.customer_management.mapper.MusteriHesapBilgileriDTO; //DTO dönüşümünde kullanıyoruz.

public interface MusteriHesapBilgileriMapper {

    //toDTO methodu,  Entity’den DTO’ya dönüştürüyor.
    //Entity içindeki alanları DTO’ya atıyoruz.

    public static MusteriHesapBilgileriDTO toDTO(MusteriHesapBilgileri entity) {
        if (entity == null) return null;

        MusteriHesapBilgileriDTO dto = new MusteriHesapBilgileriDTO();
        dto.setId(entity.getId());
        dto.setMusteriId(entity.getMusteri() != null ? entity.getMusteri().getId() : null);
        dto.setBankaAdi(entity.getBankaAdi());
        dto.setBankaSubeKodu(entity.getBankaSubeKodu());
        dto.setBankaSubeAdi(entity.getBankaSubeAdi());
        dto.setIbanNumarasi(entity.getIbanNumarasi());
        return dto;
    }

//toEntity metodu DTO’dan Entity’ye dönüştürüyor.
//Musteri parametresini dışarıdan alıyoruz çünkü DTO’da sadece ID var.

    public static MusteriHesapBilgileri toEntity(MusteriHesapBilgileriDTO dto, Musteri musteri) {
        if (dto == null) return null;

        MusteriHesapBilgileri entity = new MusteriHesapBilgileri();
        entity.setBankaAdi(dto.getBankaAdi());
        entity.setBankaSubeKodu(dto.getBankaSubeKodu());
        entity.setBankaSubeAdi(dto.getBankaSubeAdi());
        entity.setIbanNumarasi(dto.getIbanNumarasi());
        entity.setMusteri(musteri); // dışarıdan verilen Musteri entity’si
        return entity;
    }
}
