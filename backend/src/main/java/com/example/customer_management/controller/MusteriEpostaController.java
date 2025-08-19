package com.example.customer_management.controller;

import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.mapper.MusteriEpostaDTO;
import com.example.customer_management.service.MusteriEpostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musteri/{musteriId}/eposta")
public class MusteriEpostaController {

    private MusteriEpostaService musteriEpostaService;

    @Autowired
    public MusteriEpostaController(MusteriEpostaService musteriEpostaService) {
        this.musteriEpostaService = musteriEpostaService;
    }

    @PostMapping
    public ResponseEntity<MusteriEpostaDTO> createEposta(@PathVariable String musteriId,
                                                         @Valid @RequestBody MusteriEpostaDTO epostaDTO) {
        MusteriEpostaDTO created = musteriEpostaService.createMusteriEposta(musteriId,epostaDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusteriEpostaDTO> updateMusteriEposta(
            @PathVariable String musteriId,
            @PathVariable Long id,
            @Valid @RequestBody MusteriEpostaDTO epostaDTO) {

        MusteriEpostaDTO updated = musteriEpostaService.updateMusteriEposta(musteriId,id, epostaDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusteriEpostaDTO> getEpostaById(@PathVariable String musteriId,
                                                          @PathVariable Long id) {
        MusteriEpostaDTO eposta = musteriEpostaService.getMusteriEpostaById(musteriId,id);
        return ResponseEntity.ok(eposta);
    }

    @GetMapping
    public ResponseEntity<List<MusteriEpostaDTO>> getEpostalarByMusteri(@PathVariable String musteriId) {
        List<MusteriEpostaDTO> epostalar = musteriEpostaService.getAllEpostalarByMusteriId(musteriId);
        return ResponseEntity.ok(epostalar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEposta(@PathVariable String musteriId, @PathVariable Long id) {
        musteriEpostaService.deleteMusteriEposta(musteriId, id);
        return ResponseEntity.noContent().build();
    }
}
