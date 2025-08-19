package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriHesapBilgileri;
import com.example.customer_management.domain.MusteriTelefon;
import com.example.customer_management.mapper.MusteriHesapBilgileriDTO;
import com.example.customer_management.mapper.MusteriHesapBilgileriMapper;
import com.example.customer_management.repository.MusteriHesapBilgileriRepository;
import com.example.customer_management.repository.MusteriRepository;
import com.example.customer_management.service.MusteriHesapBilgileriService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional

public class MusteriHesapBilgileriServiceImpl implements MusteriHesapBilgileriService {

    private final MusteriHesapBilgileriRepository hesapBilgileriRepository;
    private final MusteriRepository musteriRepository;

    public MusteriHesapBilgileriServiceImpl(MusteriHesapBilgileriRepository hesapBilgileriRepository,
                                            MusteriRepository musteriRepository){
        this.hesapBilgileriRepository = hesapBilgileriRepository;
        this.musteriRepository = musteriRepository;
    }

    @Override
    public MusteriHesapBilgileriDTO create (String musteriId, MusteriHesapBilgileriDTO dto){
        validate(dto);
        //amaç: hesap bilgisi kaydı eklemeden önce, gerçekten böyle bir müşteri var mı emin olmak.
        // musteriId kullanılarak musteriRepository üzerinden veritabanında müşteri aranıyor.
        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));

        //DTO (Yani hesap bilgileri) ve müşteri nesnesi kullanılarak entity oluşturuluyor.
        MusteriHesapBilgileri entity= MusteriHesapBilgileriMapper.toEntity(dto,musteri);
        MusteriHesapBilgileri saved= hesapBilgileriRepository.save(entity); //(CRUD)
        //DTO’daki alanları MusteriHesapBilgileri entity’sine kopyalıyor.
        //
        //musteri nesnesini de ilişkili alanına (setMusteri(...)) ekliyor.

        return MusteriHesapBilgileriMapper.toDTO(saved);

        //Hazırlanan entity, hesapBilgileriRepository ile veritabanına kaydediliyor.
        //
        //save işlemi hem yeni kayıt ekleyebilir hem de id varsa güncelleme yapabilir.

    }

    //id → Güncellenecek hesap bilgisi kaydının veritabanındaki primary key değeri.
    //
    //dto → Kullanıcının gönderdiği yeni bilgiler.
    @Override
    public MusteriHesapBilgileriDTO update (String musteriId, Long id, MusteriHesapBilgileriDTO dto){
        validate(dto); //Eğer kurallara uymuyorsa burada hata fırlatılır, işlem başlamadan durur.

        validate(dto);

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));


        MusteriHesapBilgileri existing= hesapBilgileriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hesap bilgisi bulunamadı"));

        if(musteriId != null &&!musteriId.equals(existing.getMusteri().getId())){
            Musteri Musteri = musteriRepository.findById(musteriId)
                    .orElseThrow(() -> new RuntimeException("Musteri bulunamadı"));
            existing.setMusteri(musteri);
        }
        if(dto.getBankaAdi()!=null)
            existing.setBankaAdi(dto.getBankaAdi());
        if(dto.getBankaSubeAdi()!=null)
            existing.setBankaSubeAdi(dto.getBankaSubeAdi());
        if(dto.getBankaSubeKodu()!=null)
            existing.setBankaSubeAdi(dto.getBankaSubeKodu());
        if(dto.getIbanNumarasi()!=null)
            existing.setIbanNumarasi(dto.getIbanNumarasi());

        return MusteriHesapBilgileriMapper.toDTO(hesapBilgileriRepository.save(existing));
    }
    @Override
    public void delete(String musteriId, Long id) {
        if(!musteriRepository.existsById(musteriId)){
            throw new EntityNotFoundException("Müşteri bulunamadı: " + musteriId);
        }
        if (!hesapBilgileriRepository.existsById(id)) {
            throw new EntityNotFoundException("Hesap bulunamadı: " + id);
        }
        hesapBilgileriRepository.deleteById(id);
    }



    @Override
    public List<MusteriHesapBilgileriDTO> getAll(String musteriId) {
        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı: " + musteriId));

        return hesapBilgileriRepository.findAll()
                .stream()
                .map(MusteriHesapBilgileriMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public MusteriHesapBilgileriDTO getById(String musteriId, Long id) {
        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı: " + musteriId));
        MusteriHesapBilgileri existing = hesapBilgileriRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Telefon bulunamadı: " + id));

        if (!existing.getMusteri().getId().equals(musteri.getId())) {
            throw new RuntimeException("This address does not belong to the specified customer");
        }

        return hesapBilgileriRepository.findById(id)
                .map(MusteriHesapBilgileriMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Hesap bilgisi bulunamadı"));
    }





    private void validate (MusteriHesapBilgileriDTO dto){
        if(dto.getBankaAdi() ==null || dto.getBankaAdi().trim().isEmpty()){
            throw new IllegalArgumentException("Banka adı boş olamaz!");
        }
        if(dto.getBankaSubeKodu()== null || !dto.getBankaSubeKodu().matches("\\d{4}")){
            throw new IllegalArgumentException("Banka şube kodu 4 haneli rakamlardan oluşmalıdır!");
        }
        if(dto.getIbanNumarasi() ==null || !isValidIBAN(dto.getIbanNumarasi())){
            throw new IllegalArgumentException("Geçersiz IBAN numarası");
        }
    }

    // IBAN format kontrolü (TR için 26 haneli)

    private boolean isValidIBAN (String iban){
        String regex = "^TR\\d{24}$"; // TR + 24 hane
        return Pattern.matches(regex, iban);
    }


}
