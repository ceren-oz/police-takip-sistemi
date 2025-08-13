package com.example.customer_management.controller;

import com.example.customer_management.mapper.MusteriAdresDTO;
import com.example.customer_management.service.MusteriAdresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adres")
public class MusteriAdresController {

    private final MusteriAdresService musteriAdresService;

    @Autowired
    public MusteriAdresController(MusteriAdresService musteriAdresService) {
        this.musteriAdresService = musteriAdresService;
    }

    @PostMapping
    public ResponseEntity<MusteriAdresDTO> createAdres(@RequestBody MusteriAdresDTO adresDTO) {
        MusteriAdresDTO created = musteriAdresService.createAdres(adresDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusteriAdresDTO> getAdresById(@PathVariable Long id) {
        MusteriAdresDTO adresDTO = musteriAdresService.getAdresById(id);
        return ResponseEntity.ok(adresDTO);
    }

    // GET /api/adresler/musteri/{musteriId}
    @GetMapping("/musteri/{musteriId}")
    public ResponseEntity<List<MusteriAdresDTO>> getAdreslerByMusteriId(
            @PathVariable String musteriId) {

        List<MusteriAdresDTO> adresler = musteriAdresService.getAdreslerByMusteriId(musteriId);

        if (adresler.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(adresler); // 200 OK with list
    }


}
