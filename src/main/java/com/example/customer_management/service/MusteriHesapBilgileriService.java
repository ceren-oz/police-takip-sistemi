package com.example.customer_management.service;

import com.example.customer_management.mapper.MusteriHesapBilgileriDTO;

import java.util.List;

public interface MusteriHesapBilgileriService {
    MusteriHesapBilgileriDTO create(MusteriHesapBilgileriDTO dto);
    MusteriHesapBilgileriDTO update(Long id, MusteriHesapBilgileriDTO dto);
    void delete(Long id);
    MusteriHesapBilgileriDTO getById(Long id);
    List<MusteriHesapBilgileriDTO> getAll();
}
