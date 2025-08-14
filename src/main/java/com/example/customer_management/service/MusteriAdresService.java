package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.mapper.*;
import com.example.customer_management.repository.MusteriAdresRepository;
import com.example.customer_management.repository.MusteriRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusteriAdresService {

     private final MusteriAdresRepository musteriAdresRepository;
     private final MusteriRepository musteriRepository;
     private final MusteriAdresMapper musteriAdresMapper;


     @Autowired
    public MusteriAdresService(MusteriAdresRepository musteriAdresRepository,
                               MusteriAdresMapper musteriAdresMapper,
                               MusteriRepository musteriRepository) {
        this.musteriAdresRepository = musteriAdresRepository;
        this.musteriAdresMapper = musteriAdresMapper;
        this.musteriRepository = musteriRepository;
    }
 /*   @Transactional
    public MusteriAdresDTO createAdres(MusteriAdresDTO adresDTO) {

        MusteriAdres adres = musteriAdresMapper.toEntity(adresDTO);

        List<Musteri> managedMusteriler = new ArrayList<>();

        if(adresDTO.getMusteriIds()!=null && !adresDTO.getMusteriIds().isEmpty()){
            for(String musteriId : adresDTO.getMusteriIds()){
                Musteri managedMusteri = musteriRepository.findById(musteriId)
                        .orElseThrow(()-> new RuntimeException("Musteri not found with id: " + musteriId));
                managedMusteriler.add(managedMusteri);
            }
        }
        adres.setMusteriler(managedMusteriler);
        // Sync inverse side
        for (Musteri musteri : managedMusteriler) {
            if (!musteri.getAdresler().contains(adres)) {
                musteri.getAdresler().add(adres);
            }
        }
        MusteriAdres saved = musteriAdresRepository.save(adres);
        return musteriAdresMapper.toDTO(saved);
    }*/

   /* @Transactional
    public MusteriAdresDTO createAdres(String musteriId, MusteriAdresDTO adresDTO) {
        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));

        MusteriAdres adres = musteriAdresMapper.toEntity(adresDTO);
        adres.getMusteriler().add(musteri);

        // Sync inverse side
        if (!musteri.getAdresler().contains(adres)) {
            musteri.getAdresler().add(adres);
        }

        MusteriAdres saved = musteriAdresRepository.save(adres);
        return musteriAdresMapper.toDTO(saved);
    }*/

    @Transactional
    public MusteriAdresDTO createAdres(String musteriId, MusteriAdresDTO adresDTO){

        MusteriAdres adres = musteriAdresMapper.toEntity(adresDTO);

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found"));
        adres.setMusteri(musteri);

        MusteriAdres saved = musteriAdresRepository.save(adres);
        return musteriAdresMapper.toDTO(saved);
    }

    /*
    @Transactional
    public MusteriAdresDTO updateAdres(Long id, MusteriAdresDTO adresDTO){

         MusteriAdres existingAdres = musteriAdresRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("Adres not found"));

         if (adresDTO.getYazismaAdresiMi() != null){
             existingAdres.setYazismaAdresiMi(adresDTO.getYazismaAdresiMi());
         }
         if(adresDTO.getAdresKisaAd() != null){
             existingAdres.setAdresKisaAd(adresDTO.getAdresKisaAd());
         }
         if(adresDTO.getIl() != null){
             existingAdres.setIl(adresDTO.getIl());
         }
         if (adresDTO.getIlce() != null){
             existingAdres.setIlce(adresDTO.getIlce());
         }
         if (adresDTO.getCadde() != null){
             existingAdres.setCadde(adresDTO.getCadde());
         }
        if(adresDTO.getSokak() != null){
            existingAdres.setSokak(adresDTO.getSokak());
        }
        if (adresDTO.getApartmanAdi() != null){
            existingAdres.setApartmanAdi(adresDTO.getApartmanAdi());
        }
        if (adresDTO.getDaireNo() != null){
            existingAdres.setDaireNo(adresDTO.getDaireNo());
        }

        // 3. Update associated musteriler if list provided
        if (adresDTO.getMusteriIds() != null) {
            List<Musteri> managedMusteriler = new ArrayList<>();
            for (String musteriId : adresDTO.getMusteriIds()) {
                Musteri managedMusteri = musteriRepository.findById(musteriId)
                        .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));
                managedMusteriler.add(managedMusteri);
            }

            // Replace the old list
            existingAdres.setMusteriler(managedMusteriler);

            // Sync inverse side
            for (Musteri musteri : managedMusteriler) {
                if (!musteri.getAdresler().contains(existingAdres)) {
                    musteri.getAdresler().add(existingAdres);
                }
            }
        }

        // 4. Save and return updated DTO
        MusteriAdres updated = musteriAdresRepository.save(existingAdres);
        return musteriAdresMapper.toDTO(updated);

    }*/
/*
    @Transactional
    public MusteriAdresDTO updateAdres(String musteriId, Long id, MusteriAdresDTO adresDTO) {
        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));

        MusteriAdres existingAdres = musteriAdresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adres not found"));

        // Optional: sadece bu müşteriye ait adresler güncellenebilir
        if (!existingAdres.getMusteriler().contains(musteri)) {
            throw new RuntimeException("This address does not belong to the specified customer");
        }

        if (adresDTO.getYazismaAdresiMi() != null) existingAdres.setYazismaAdresiMi(adresDTO.getYazismaAdresiMi());
        if (adresDTO.getAdresKisaAd() != null) existingAdres.setAdresKisaAd(adresDTO.getAdresKisaAd());
        if (adresDTO.getIl() != null) existingAdres.setIl(adresDTO.getIl());
        if (adresDTO.getIlce() != null) existingAdres.setIlce(adresDTO.getIlce());
        if (adresDTO.getCadde() != null) existingAdres.setCadde(adresDTO.getCadde());
        if (adresDTO.getSokak() != null) existingAdres.setSokak(adresDTO.getSokak());
        if (adresDTO.getApartmanAdi() != null) existingAdres.setApartmanAdi(adresDTO.getApartmanAdi());
        if (adresDTO.getDaireNo() != null) existingAdres.setDaireNo(adresDTO.getDaireNo());

        MusteriAdres updated = musteriAdresRepository.save(existingAdres);
        return musteriAdresMapper.toDTO(updated);
    }*/

    @Transactional
    public MusteriAdresDTO updateAdres(String musteriId, Long id,
                                       MusteriAdresDTO adresDTO) {
        // Find existing email
        MusteriAdres existingAdres = musteriAdresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri adres not found"));

        // Update associated customer if musteriId is provided and different
        if (musteriId != null &&
                (existingAdres.getMusteri() == null ||
                        !musteriId.equals(existingAdres.getMusteri().getId()))) {

            Musteri musteri = musteriRepository.findById(musteriId)
                    .orElseThrow(() -> new RuntimeException("Musteri not found"));
            existingAdres.setMusteri(musteri);
        }

        if (adresDTO.getYazismaAdresiMi() != null) existingAdres.setYazismaAdresiMi(adresDTO.getYazismaAdresiMi());
        if (adresDTO.getAdresKisaAd() != null) existingAdres.setAdresKisaAd(adresDTO.getAdresKisaAd());
        if (adresDTO.getIl() != null) existingAdres.setIl(adresDTO.getIl());
        if (adresDTO.getIlce() != null) existingAdres.setIlce(adresDTO.getIlce());
        if (adresDTO.getCadde() != null) existingAdres.setCadde(adresDTO.getCadde());
        if (adresDTO.getSokak() != null) existingAdres.setSokak(adresDTO.getSokak());
        if (adresDTO.getApartmanAdi() != null) existingAdres.setApartmanAdi(adresDTO.getApartmanAdi());
        if (adresDTO.getDaireNo() != null) existingAdres.setDaireNo(adresDTO.getDaireNo());



        MusteriAdres updated = musteriAdresRepository.save(existingAdres);
        return musteriAdresMapper.toDTO(updated);
    }

    @Transactional(readOnly = true)
    public List<MusteriAdresDTO> getAdreslerByMusteriId(String musteriId) {

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));


        List<MusteriAdres> adresler = new ArrayList<>(musteri.getAdresler());

        // Convert entities to DTOs
        return adresler.stream()
                .map(musteriAdresMapper::toDTO)
                .collect(Collectors.toList());
    }


  /*  public MusteriAdresDTO getAdresById(Long id) {
        MusteriAdres adres = musteriAdresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adres not found"));
        return musteriAdresMapper.toDTO(adres);
    }*/

    @Transactional(readOnly = true)
    public MusteriAdresDTO getAdresById(String musteriId, Long id) {
        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));

        MusteriAdres adres = musteriAdresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adres not found"));

        if (!adres.getMusteri().getId().equals(musteri.getId())) {
            throw new RuntimeException("This address does not belong to the specified customer");
        }

        return musteriAdresMapper.toDTO(adres);
    }

    @Transactional
    public void deleteMusteriAdres(String musteriId, Long id) {

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));

        MusteriAdres adres = musteriAdresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri e-posta not found"));

        if (!adres.getMusteri().getId().equals(musteri.getId())) {
            throw new RuntimeException("This address does not belong to the specified customer");
        }
        musteriAdresRepository.deleteById(id);
    }

}
