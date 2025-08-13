package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.mapper.MusteriDTO;
import com.example.customer_management.mapper.MusteriEpostaMapper;
import com.example.customer_management.mapper.MusteriMapper;
import com.example.customer_management.repository.MusteriAdresRepository;
import com.example.customer_management.repository.MusteriEpostaRepository;
import com.example.customer_management.repository.MusteriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusteriService {

    private final MusteriRepository musteriRepository;
    private final MusteriAdresRepository musteriAdresRepository;
    private final MusteriMapper musteriMapper;

    private final MusteriEpostaRepository musteriEpostaRepository;


    @Autowired
    public MusteriService(MusteriRepository musteriRepository,
                          MusteriAdresRepository musteriAdresRepository,
                           MusteriMapper musteriMapper,
                          MusteriEpostaRepository musteriEpostaRepository)
    {
        this.musteriRepository = musteriRepository;
        this.musteriAdresRepository = musteriAdresRepository;
        this.musteriMapper = musteriMapper;
        this.musteriEpostaRepository = musteriEpostaRepository;
    }
  /*  @Transactional
    public MusteriDTO createMusteri(MusteriDTO musteriDTO) {
        Musteri musteri = musteriMapper.toEntity(musteriDTO);

        // Handle addresses
        if (musteriDTO.getAdresler() != null && !musteriDTO.getAdresler().isEmpty()) {
            List<MusteriAdres> adresler = new ArrayList<>();

            for (MusteriAdres adres : musteriDTO.getAdresler()) {
                if (adres.getId() == null) {
                    throw new RuntimeException("Adres id is null");
                }
                MusteriAdres managedAdres = musteriAdresRepository.findById(adres.getId())
                        .orElseThrow(() -> new RuntimeException("MusteriAdres not found with id: " + adres.getId()));
                adresler.add(managedAdres);
            }

            musteri.setAdresler(adresler);

            // Sync inverse side
            for (MusteriAdres adres : adresler) {
                if (!adres.getMusteriler().contains(musteri)) {
                    adres.getMusteriler().add(musteri);
                }
            }
        }

        // Handle e-mails
        if (musteriDTO.getEpostalar() != null && !musteriDTO.getEpostalar().isEmpty()) {
            List<MusteriEposta> epostaEntities = new ArrayList<>();

            for (MusteriEposta eposta : musteriDTO.getEpostalar()) {
                MusteriEposta managedEposta;
                if (eposta.getId() != null) {
                    managedEposta = musteriEpostaRepository.findById(eposta.getId())
                            .orElse(eposta);
                } else {
                    managedEposta = eposta; // new entity case
                }
                managedEposta.setMusteri(musteri);
                epostaEntities.add(managedEposta);
            }

            musteri.setEpostalar(epostaEntities);
        }

        Musteri saved = musteriRepository.save(musteri);
        return musteriMapper.toDTO(saved);
    }*/

    @Transactional
    public MusteriDTO createMusteri(MusteriDTO musteriDTO) {
        Musteri musteri = musteriMapper.toEntity(musteriDTO);

        // Handle addresses via adresIds
        if (musteriDTO.getAdresIds() != null && !musteriDTO.getAdresIds().isEmpty()) {
            List<MusteriAdres> adresler = musteriDTO.getAdresIds().stream()
                    .map(id -> musteriAdresRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("MusteriAdres not found with id: " + id)))
                    .collect(Collectors.toList());

            musteri.setAdresler(adresler);

            // Sync inverse side
            for (MusteriAdres adres : adresler) {
                if (!adres.getMusteriler().contains(musteri)) {
                    adres.getMusteriler().add(musteri);
                }
            }
        }

        // Handle e-mails via epostaIds
        if (musteriDTO.getEpostaIds() != null && !musteriDTO.getEpostaIds().isEmpty()) {
            List<MusteriEposta> epostaEntities = musteriDTO.getEpostaIds().stream()
                    .map(id -> musteriEpostaRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("MusteriEposta not found with id: " + id)))
                    .peek(eposta -> eposta.setMusteri(musteri)) // set relation
                    .collect(Collectors.toList());

            musteri.setEpostalar(epostaEntities);
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
