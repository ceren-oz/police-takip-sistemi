package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.repository.MusteriRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusteriService {

    private final MusteriRepository musteriRepository;


    public MusteriService(MusteriRepository musteriRepository) {
        this.musteriRepository = musteriRepository;
    }

    public Musteri createMusteri(Musteri musteri){
        return musteriRepository.save(musteri);
    }

    public List<Musteri> getAllMusteri(){
        return musteriRepository.findAll();
    }
}
