package com.example.customer_management.controller;

import com.example.customer_management.mapper.AracBilgileriDTO;
import com.example.customer_management.service.AracBilgileriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//POST   /api/araclar
//PUT    /api/araclar/{id}
//GET    /api/araclar/{id}
//GET    /api/araclar
//DELETE /api/araclar/{id}

@RestController
@RequestMapping("/api/araclar")
public class AracBilgileriController {

    private final AracBilgileriService aracService;

    @Autowired
    public AracBilgileriController(AracBilgileriService aracService) {
        this.aracService = aracService;
    }

    @PostMapping
    public ResponseEntity<AracBilgileriDTO> createArac(@RequestBody AracBilgileriDTO dto) {
        AracBilgileriDTO saved = aracService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AracBilgileriDTO> updateArac(@PathVariable Long id,
                                                       @RequestBody AracBilgileriDTO dto) {
        AracBilgileriDTO updated = aracService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AracBilgileriDTO> getAracById(@PathVariable Long id) {
        AracBilgileriDTO arac = aracService.getById(id);
        return ResponseEntity.ok(arac);
    }

    @GetMapping
    public ResponseEntity<List<AracBilgileriDTO>> getAllAraclar() {
        List<AracBilgileriDTO> list = aracService.getAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArac(@PathVariable Long id) {
        aracService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
