package com.example.customer_management.controller;

import com.example.customer_management.mapper.TeminatDTO;
import com.example.customer_management.service.TeminatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//POST   /api/teminatlar
//PUT    /api/teminatlar/{id}
//GET    /api/teminatlar/{id}
//GET    /api/teminatlar
//DELETE /api/teminatlar/{id}

@RestController
@RequestMapping("/api/teminatlar")
public class TeminatController {

    private final TeminatService teminatService;

    @Autowired
    public TeminatController(TeminatService teminatService) {
        this.teminatService = teminatService;
    }

    @PostMapping
    public ResponseEntity<TeminatDTO> createTeminat(@RequestBody TeminatDTO dto) {
        TeminatDTO saved = teminatService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeminatDTO> updateTeminat(@PathVariable Long id,
                                                    @RequestBody TeminatDTO dto) {
        TeminatDTO updated = teminatService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeminatDTO> getTeminatById(@PathVariable Long id) {
        TeminatDTO teminat = teminatService.getById(id);
        return ResponseEntity.ok(teminat);
    }

    @GetMapping
    public ResponseEntity<List<TeminatDTO>> getAllTeminatlar() {
        List<TeminatDTO> list = teminatService.getAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeminat(@PathVariable Long id) {
        teminatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
