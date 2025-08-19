package com.example.customer_management.controller;

import com.example.customer_management.mapper.MusteriHesapBilgileriDTO;
import com.example.customer_management.service.MusteriHesapBilgileriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Controller olduğunu belirtir
@RequestMapping("/api/musteriler/{musteriId}/hesap-bilgileri") // URL prefix
//Akış:
//Client (frontend/Postman) → Controller → Service → Repository → DB → Service → Controller → Client
public class MusteriHesapBilgileriController {
    private final MusteriHesapBilgileriService hesapBilgileriService;

    public MusteriHesapBilgileriController(MusteriHesapBilgileriService hesapBilgileriService) {
        this.hesapBilgileriService = hesapBilgileriService;
    }

    // Yeni hesap bilgisi ekleme
    //POST /api/musteriler/{musteriId}/hesap-bilgileri
    @PostMapping
    public MusteriHesapBilgileriDTO create(@PathVariable String musteriId, @RequestBody MusteriHesapBilgileriDTO dto) {
        return hesapBilgileriService.create(musteriId, dto);
    }

    // Tüm hesap bilgilerini listeleme
    //GET /api/musteriler/{musteriId}/hesap-bilgileri
    @GetMapping
    public List<MusteriHesapBilgileriDTO> getAll(@PathVariable String musteriId) {
        return hesapBilgileriService.getAll(musteriId);
    }

    // Tek bir hesap bilgisi getirme (ID ile)
    //GET /api/musteriler/{musteriId}/hesap-bilgileri/{id}
    @GetMapping("/{id}")
    public MusteriHesapBilgileriDTO getById(@PathVariable String musteriId, @PathVariable Long id) {
        return hesapBilgileriService.getById(musteriId,id);
    }

    // Hesap bilgisi güncelleme
    //PUT /api/musteriler/{musteriId}/hesap-bilgileri/{id}
    @PutMapping("/{id}")
    public MusteriHesapBilgileriDTO update(@PathVariable String musteriId, @PathVariable Long id, @RequestBody MusteriHesapBilgileriDTO dto) {
        return hesapBilgileriService.update(musteriId, id, dto);
    }

    // Hesap bilgisi silme
    //DELETE /api/musteriler/{musteriId}/hesap-bilgileri/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String musteriId, @PathVariable Long id) {
        hesapBilgileriService.delete(musteriId, id);
    }
}
