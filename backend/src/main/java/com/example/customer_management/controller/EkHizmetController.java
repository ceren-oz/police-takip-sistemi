package com.example.customer_management.controller;

import com.example.customer_management.mapper.EkHizmetDTO;
import com.example.customer_management.mapper.MusteriEpostaDTO;
import com.example.customer_management.service.EkHizmetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ekhizmet")
public class EkHizmetController {

    private final EkHizmetService ekHizmetService;

    @Autowired
    public EkHizmetController(EkHizmetService ekHizmetService) {
        this.ekHizmetService = ekHizmetService;
    }

    @PostMapping
    public ResponseEntity<EkHizmetDTO> createEkHizmet(@RequestBody EkHizmetDTO dto) {
        EkHizmetDTO created = ekHizmetService.createEkHizmet(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EkHizmetDTO> updateEkHizmet(
            @PathVariable Long id,
            @RequestBody EkHizmetDTO hizmetDTO) {

        EkHizmetDTO updated = ekHizmetService.updateEkHizmet(id, hizmetDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<EkHizmetDTO>> getAllEkHizmetler() {
        List<EkHizmetDTO> list = ekHizmetService.getAllEkHizmetler();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EkHizmetDTO> getByEkHizmetId(@PathVariable Long id) {
        return ResponseEntity.ok(ekHizmetService.getByEkHizmetId(id));
    }


    @GetMapping("/police/{policeId}")
    public ResponseEntity<List<EkHizmetDTO>> getEkHizmetlerByPoliceId(@PathVariable Long policeId) {
        return ResponseEntity.ok(ekHizmetService.getEkHizmetlerByPoliceId(policeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEkHizmet(@PathVariable Long id) {
        ekHizmetService.deleteEkHizmet(id);
        return ResponseEntity.noContent().build();
    }
}
