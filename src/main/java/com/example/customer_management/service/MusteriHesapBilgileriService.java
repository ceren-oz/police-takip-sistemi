package com.example.customer_management.service;

import com.example.customer_management.mapper.MusteriHesapBilgileriDTO;

import java.util.List;

public interface MusteriHesapBilgileriService {
    MusteriHesapBilgileriDTO create(String musteriId, MusteriHesapBilgileriDTO dto);
    MusteriHesapBilgileriDTO update(String musteriId, Long id, MusteriHesapBilgileriDTO dto);
    void delete(String musteriId, Long id);
    MusteriHesapBilgileriDTO getById(String musteriId, Long id);
    List<MusteriHesapBilgileriDTO> getAll(String musteriId);
}
