
package com.example.customer_management.controller;

import com.example.customer_management.mapper.MusteriTelefonDTO;
import com.example.customer_management.mapper.MusteriTelefonMapper;
import com.example.customer_management.service.MusteriTelefonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controller olduğunu belirtir
@RequestMapping("/api/musteri/{musteriId}/telefonlar") // URL prefix
@CrossOrigin(origins = "http://localhost:3000")
public class MusteriTelefonController {

    private final MusteriTelefonService telefonService;

    public MusteriTelefonController(MusteriTelefonService telefonService) {
        this.telefonService = telefonService;
    }

    // Yeni telefon ekleme
    //POST /api/musteriler/{musteriId}/telefonlar → yeni telefon ekle
    @PostMapping
    public MusteriTelefonDTO addTelefon(@PathVariable String musteriId, @RequestBody MusteriTelefonDTO dto) {
        return telefonService.addTelefonToMusteri(musteriId, dto);
    }

    // Müşterinin tüm telefonlarını listeleme
    /// GET /api/musteriler/{musteriId}/telefonlar → tüm telefonları getir
    @GetMapping
    public List<MusteriTelefonDTO> getTelefonlar(@PathVariable String musteriId) {
        return telefonService.getTelefonlarByMusteri(musteriId);
    }


    // Tek bir telefon kaydını müşteri ve telefon ID ile getir
    //GET /api/musteriler/{musteriId}/telefonlar/{telefonId} → tek telefon getir
    @GetMapping("/{telefonId}")
    public MusteriTelefonDTO getMusteriTelefonById(@PathVariable String musteriId,
                                                   @PathVariable Long telefonId) {
        return telefonService.getMusteriTelefonById(musteriId, telefonId);
    }


    // Telefon güncelleme
    //PUT /api/musteriler/{musteriId}/telefonlar/{telefonId} → telefon güncelle
    @PutMapping("/{telefonId}")
    public MusteriTelefonDTO updateTelefon(@PathVariable String musteriId, @PathVariable Long telefonId, @RequestBody MusteriTelefonDTO dto) {
        return telefonService.updateTelefon(musteriId,telefonId, dto);
    }

    // Telefon silme
    //@DeleteMapping("/{telefonId}")
    @DeleteMapping("/{telefonId}")
    public void deleteTelefon(@PathVariable String musteriId, @PathVariable Long telefonId) {
        telefonService.deleteTelefon(musteriId, telefonId);
    }
}