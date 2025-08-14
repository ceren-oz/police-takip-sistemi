package com.example.customer_management.controller;

import com.example.customer_management.mapper.MusteriHesapBilgileriDTO;
import com.example.customer_management.service.MusteriHesapBilgileriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Controller olduğunu belirtir
@RequestMapping("/api/musteri-hesap-bilgileri") // URL prefix
//Akış:
//Client (frontend/Postman) → Controller → Service → Repository → DB → Service → Controller → Client
public class MusteriHesapBilgileriController {
    private final MusteriHesapBilgileriService hesapBilgileriService;

    public MusteriHesapBilgileriController(MusteriHesapBilgileriService hesapBilgileriService) {
        this.hesapBilgileriService = hesapBilgileriService;
    }

    // Yeni hesap bilgisi ekleme
    @PostMapping
    public MusteriHesapBilgileriDTO create(@RequestBody MusteriHesapBilgileriDTO dto) {
        return hesapBilgileriService.create(dto);
    }

    // Tüm hesap bilgilerini listeleme
    @GetMapping
    public List<MusteriHesapBilgileriDTO> getAll() {
        return hesapBilgileriService.getAll();
    }

    // Tek bir hesap bilgisi getirme (ID ile)
    @GetMapping("/{id}")
    public MusteriHesapBilgileriDTO getById(@PathVariable Long id) {
        return hesapBilgileriService.getById(id);
    }

    // Hesap bilgisi güncelleme
    @PutMapping("/{id}")
    public MusteriHesapBilgileriDTO update(@PathVariable Long id, @RequestBody MusteriHesapBilgileriDTO dto) {
        return hesapBilgileriService.update(id, dto);
    }

    // Hesap bilgisi silme
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hesapBilgileriService.delete(id);
    }
}
