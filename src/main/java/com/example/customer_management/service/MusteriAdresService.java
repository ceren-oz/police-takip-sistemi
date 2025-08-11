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

        // For each Musteri in DTO, fetch from DB and add to managed list
        for (Musteri m : adresDTO.getMusteriler()) {
            Musteri managedMusteri = musteriRepository.findById(m.getId())
                    .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + m.getId()));
            managedMusteriler.add(managedMusteri);
        }

        // Set managed Musteri list into MusteriAdres
        adres.setMusteriler(managedMusteriler);

        // Also update the inverse side of the relationship for each Musteri
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
