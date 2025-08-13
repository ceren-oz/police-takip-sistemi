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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdres(@PathVariable Long id) {
        musteriAdresService.deleteAdres(id);
        return ResponseEntity.noContent().build();
    }
}
