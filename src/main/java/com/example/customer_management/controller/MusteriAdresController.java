package com.example.customer_management.controller;

import com.example.customer_management.mapper.MusteriAdresDTO;
import com.example.customer_management.mapper.MusteriEpostaDTO;
import com.example.customer_management.service.MusteriAdresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musteri/{musteriId}/adres")
public class MusteriAdresController {

    private final MusteriAdresService musteriAdresService;

    @Autowired
    public MusteriAdresController(MusteriAdresService musteriAdresService) {
        this.musteriAdresService = musteriAdresService;
    }

    @PostMapping/*("musteri/{musteriId}")*/
    public ResponseEntity<MusteriAdresDTO> createAdres(@PathVariable String musteriId,
            @RequestBody MusteriAdresDTO adresDTO) {
        MusteriAdresDTO created = musteriAdresService.createAdres(musteriId,adresDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")/*("/musteri/{musteriId}/{id}")*/
    public ResponseEntity<MusteriAdresDTO> updateMusteriAdres(
            @PathVariable String musteriId,
            @PathVariable Long id,
            @RequestBody MusteriAdresDTO adresDTO) {

        MusteriAdresDTO updated = musteriAdresService.updateAdres(musteriId,id, adresDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")/*("/musteri/{musteriId}/{id}")*/
    public ResponseEntity<MusteriAdresDTO> getAdresById(@PathVariable String musteriId,
                                                        @PathVariable Long id) {
        MusteriAdresDTO adresDTO = musteriAdresService.getAdresById(musteriId, id);
        return ResponseEntity.ok(adresDTO);
    }

    // GET /api/adresler/musteri/{musteriId}
    @GetMapping/*("/musteri/{musteriId}")*/
    public ResponseEntity<List<MusteriAdresDTO>> getAdreslerByMusteriId(
            @PathVariable String musteriId) {

        List<MusteriAdresDTO> adresler = musteriAdresService.getAdreslerByMusteriId(musteriId);

        if (adresler.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(adresler); // 200 OK with list
    }

    @DeleteMapping("/{id}")/*("/musteri/{musteriId}/{id}")*/
    public ResponseEntity<Void> deleteAdres(@PathVariable String musteriId, @PathVariable Long id) {
        musteriAdresService.deleteMusteriAdres(musteriId, id);
        return ResponseEntity.noContent().build();
    }


}
