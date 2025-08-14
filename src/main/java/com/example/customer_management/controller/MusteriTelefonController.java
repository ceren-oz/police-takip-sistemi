
package com.example.customer_management.controller;

import com.example.customer_management.mapper.MusteriTelefonDTO;
import com.example.customer_management.service.MusteriTelefonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controller olduğunu belirtir
@RequestMapping("/api/musteri-telefon") // URL prefix
public class MusteriTelefonController {

    private final MusteriTelefonService telefonService;

    public MusteriTelefonController(MusteriTelefonService telefonService) {
        this.telefonService = telefonService;
    }

    // Yeni telefon ekleme
    @PostMapping("/musteri/{musteriId}")
    public MusteriTelefonDTO addTelefon(@PathVariable String musteriId, @RequestBody MusteriTelefonDTO dto) {
        return telefonService.addTelefonToMusteri(musteriId, dto);
    }

    // Müşterinin tüm telefonlarını listeleme
    @GetMapping("/{musteriId}")
    public List<MusteriTelefonDTO> getTelefonlar(@PathVariable String musteriId) {
        return telefonService.getTelefonlarByMusteri(musteriId);
    }

    // Telefon güncelleme
    @PutMapping("/{telefonId}")
    public MusteriTelefonDTO updateTelefon(@PathVariable Long telefonId, @RequestBody MusteriTelefonDTO dto) {
        return telefonService.updateTelefon(telefonId, dto);
    }

    // Telefon silme
    @DeleteMapping("/{telefonId}")
    public void deleteTelefon(@PathVariable Long telefonId) {
        telefonService.deleteTelefon(telefonId);
    }
}