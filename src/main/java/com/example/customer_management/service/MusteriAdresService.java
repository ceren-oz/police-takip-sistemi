package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.mapper.MusteriAdresDTO;
import com.example.customer_management.mapper.MusteriAdresMapper;
import com.example.customer_management.repository.MusteriAdresRepository;
import com.example.customer_management.repository.MusteriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public MusteriAdresDTO createAdres(MusteriAdresDTO adresDTO) {

        MusteriAdres adres = musteriAdresMapper.toEntity(adresDTO);

        List<Musteri> managedMusteriler = new ArrayList<>();

        // Fetch managed Musteri entities by IDs extracted from DTO entities
        for (Musteri musteri : adresDTO.getMusteriler()) {
            if (musteri.getId() == null) {
                throw new RuntimeException("Musteri id is null");
            }
            Musteri managedMusteri = musteriRepository.findById(musteri.getId())
                    .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteri.getId()));
            managedMusteriler.add(managedMusteri);
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
    }



    /*public void deleteAdres(Long id) {
        musteriAdresRepository.deleteById(id);
    }*/

    public MusteriAdresDTO getAdresById(Long id) {
        MusteriAdres adres = musteriAdresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adres not found"));
        return musteriAdresMapper.toDTO(adres);
    }
}
