package com.example.customer_management.controller;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.mapper.MusteriDTO;
import com.example.customer_management.service.MusteriService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Postman'den test edildi -> get ve post çalışıyor
@RestController
@RequestMapping("/api/musteri")
public class MusteriController {

    private MusteriService musteriService;

    @Autowired
    public MusteriController(MusteriService musteriService) {
        this.musteriService = musteriService;
    }

    @PostMapping
    public ResponseEntity<MusteriDTO> createMusteri(@Valid @RequestBody MusteriDTO musteriDTO) {
        MusteriDTO created = musteriService.createMusteri(musteriDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusteriDTO> getMusteriById(@PathVariable String id) {
        MusteriDTO musteriDTO = musteriService.getMusteriById(id);
        return ResponseEntity.ok(musteriDTO);
    }

    /*@GetMapping
    public List<Musteri> getAllMusteri(){
        return musteriService.getAllMusteri();
    }*/


}
