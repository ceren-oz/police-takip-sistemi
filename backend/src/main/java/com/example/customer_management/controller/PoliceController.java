package com.example.customer_management.controller;

import com.example.customer_management.mapper.PoliceDTO;
import com.example.customer_management.service.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policeler")
public class PoliceController {
    private final PoliceService policeService;

    @Autowired
    public PoliceController(PoliceService policeService) {
        this.policeService = policeService;
    }


    @PostMapping
    public ResponseEntity<PoliceDTO> createPolice(@RequestBody PoliceDTO dto) {
        PoliceDTO savedPolice = policeService.createPolice(dto);
        return ResponseEntity.ok(savedPolice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PoliceDTO> updatePolice(@PathVariable Long id,
                                                  @RequestBody PoliceDTO dto) {
        PoliceDTO updated = policeService.updatePolice(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliceDTO> getPoliceById(@PathVariable Long id) {
        PoliceDTO police = policeService.getPoliceById(id);
        return ResponseEntity.ok(police);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolice(@PathVariable Long id) {
        policeService.deletePolice(id);
        return ResponseEntity.noContent().build();
    }
}
