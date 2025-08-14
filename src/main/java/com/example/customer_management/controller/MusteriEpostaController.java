package com.example.customer_management.controller;

import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.mapper.MusteriEpostaDTO;
import com.example.customer_management.service.MusteriEpostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eposta")
public class MusteriEpostaController {

    private MusteriEpostaService musteriEpostaService;

    @Autowired
    public MusteriEpostaController(MusteriEpostaService musteriEpostaService) {
        this.musteriEpostaService = musteriEpostaService;
    }

    @PostMapping
    public ResponseEntity<MusteriEpostaDTO> createEposta(@RequestBody MusteriEpostaDTO epostaDTO) {
        MusteriEpostaDTO created = musteriEpostaService.createMusteriEposta(epostaDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusteriEpostaDTO> updateMusteriEposta(
            @PathVariable Long id,
            @RequestBody MusteriEpostaDTO epostaDTO) {

        MusteriEpostaDTO updated = musteriEpostaService.updateMusteriEposta(id, epostaDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusteriEpostaDTO> getEpostaById(@PathVariable Long id) {
        MusteriEpostaDTO eposta = musteriEpostaService.getMusteriEpostaById(id);
        return ResponseEntity.ok(eposta);
    }

    @GetMapping("/musteri/{id}")
    public ResponseEntity<List<MusteriEpostaDTO>> getEpostalarByMusteri(@PathVariable("id") String musteriId) {
        List<MusteriEpostaDTO> epostalar = musteriEpostaService.getAllEpostalarByMusteriId(musteriId);
        return ResponseEntity.ok(epostalar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEposta(@PathVariable Long id) {
        musteriEpostaService.deleteMusteriEposta(id);
        return ResponseEntity.noContent().build();
    }
}
