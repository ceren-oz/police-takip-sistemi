package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.mapper.MusteriDTO;
import com.example.customer_management.mapper.MusteriMapper;
import com.example.customer_management.repository.MusteriAdresRepository;
import com.example.customer_management.repository.MusteriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusteriService {

    private final MusteriRepository musteriRepository;
    private final MusteriAdresRepository musteriAdresRepository;
    private final MusteriMapper musteriMapper;


    @Autowired
    public MusteriService(MusteriRepository musteriRepository,
                          MusteriAdresRepository musteriAdresRepository,
                          MusteriMapper musteriMapper)
    {
        this.musteriRepository = musteriRepository;
        this.musteriAdresRepository = musteriAdresRepository;
        this.musteriMapper = musteriMapper;
    }


    @Transactional
    public MusteriDTO createMusteri(MusteriDTO musteriDTO) {
        Musteri musteri = musteriMapper.toEntity(musteriDTO);

        if (musteriDTO.getAdresIds() != null && !musteriDTO.getAdresIds().isEmpty()) {
            // Fetch managed MusteriAdres entities by IDs
            List<MusteriAdres> adresler = musteriAdresRepository.findAllById(musteriDTO.getAdresIds());

            // Set to musteri
            musteri.setAdresler(adresler);

            // Sync inverse side: add musteri to each MusteriAdres's musteriler list
            for (MusteriAdres adres : adresler) {
                if (!adres.getMusteriler().contains(musteri)) {
                    adres.getMusteriler().add(musteri);
                }
            }
        }

        Musteri saved = musteriRepository.save(musteri);
        return musteriMapper.toDTO(saved);
    }


    public MusteriDTO getMusteriById(String id) {
        Musteri musteri = musteriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri not found"));
        return musteriMapper.toDTO(musteri);
    }

   /* public List<Musteri> getAllMusteri(){
        return musteriRepository.findAll();
    }*/
}
