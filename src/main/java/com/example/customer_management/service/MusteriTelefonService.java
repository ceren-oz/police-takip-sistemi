package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriTelefon;
import com.example.customer_management.mapper.MusteriTelefonDTO;
import com.example.customer_management.mapper.MusteriTelefonMapper;
import com.example.customer_management.repository.MusteriRepository;
import com.example.customer_management.repository.MusteriTelefonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Bu sınıfı Spring’in service katmanı olarak işaretler

public class MusteriTelefonService {

    private final MusteriTelefonRepository telefonRepository;
    private final MusteriRepository musteriRepository;

    // Constructor Injection = Bağımlılıkları (repository) sınıfa enjekte eder
    public MusteriTelefonService(MusteriTelefonRepository telefonRepository,
                                 MusteriRepository musteriRepository) {
        this.telefonRepository = telefonRepository;
        this.musteriRepository = musteriRepository;
    }

    //  Yeni telefon ekleme
    public MusteriTelefonDTO addTelefonToMusteri(String musteriId, MusteriTelefonDTO telefonDTO) {
        // 1- Önce müşteri var mı diye kontrol et
        Musteri musteri = musteriRepository.findById(musteriId)
                // Eğer yoksa EntityNotFoundException fırlat (hazır bir exception)
                .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı: " + musteriId));

        // 2- DTO → Entity dönüşümü (hazır mapper metodunu kullanıyoruz)
        MusteriTelefon entity = MusteriTelefonMapper.toEntity(telefonDTO);

        // 3- Foreign key bağlama (çok önemli) not aşağıda
        entity.setMusteri(musteri);

        // 4- Veritabanına kaydet → Save metodu JpaRepository’den gelir
        MusteriTelefon saved = telefonRepository.save(entity);

        // 5- Entity → DTO dönüşümü ile sonucu döndür
        return MusteriTelefonMapper.toDTO(saved);
    }

    //  Müşterinin tüm telefonlarını getir
    public List<MusteriTelefonDTO> getTelefonlarByMusteri(String musteriId) {
        return telefonRepository.findByMusteri_Id(musteriId) // Repository metodunu çağır
                .stream() // Listeyi stream'e çevir
                .map(MusteriTelefonMapper::toDTO) // Her entity’yi DTO’ya çevir
                .collect(Collectors.toList()); // Listeye geri topla
    }

    //  Telefon güncelleme
    public MusteriTelefonDTO updateTelefon(Long telefonId, MusteriTelefonDTO telefonDTO) {
        MusteriTelefon existing = telefonRepository.findById(telefonId)
                .orElseThrow(() -> new EntityNotFoundException("Telefon bulunamadı: " + telefonId));

        existing.setIletisimTelefonuMu(telefonDTO.getIletisimTelefonuMu());
        existing.setTelefonTipi(telefonDTO.getTelefonTipi());
        existing.setUlkeKodu(telefonDTO.getUlkeKodu());
        existing.setAlanKodu(telefonDTO.getAlanKodu());
        existing.setTelefonNumarasi(telefonDTO.getTelefonNumarasi());

        return MusteriTelefonMapper.toDTO(telefonRepository.save(existing));
    }

    //  Telefon silme
    public void deleteTelefon(Long telefonId) {
        if (!telefonRepository.existsById(telefonId)) {
            throw new EntityNotFoundException("Telefon bulunamadı: " + telefonId);
        }
        telefonRepository.deleteById(telefonId);
    }

}
//// 3- Foreign key bağlama (çok önemli) not aşağıda
//        entity.setMusteri(musteri);
//@ManyToOne ilişkisi MusteriTelefon sınıfında var evet, ama burada yalnızca ilişkinin var olduğunu söylüyorsun.
//
//Spring/JPA tek başına, “şu telefon kaydının hangi müşteriye ait olduğunu” kendiliğinden anlamaz.
//Bunu bizim kodda belirtmemiz gerekir.
//
//Bir telefon eklerken hangi müşteriye ait olduğunu setMusteri(musteriObjesi) ile söylemezsen, foreign key alanı boş kalır ve kayıt yapılamaz.
//
//O yüzden service içinde bu satır var

