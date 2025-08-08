package com.example.customer_management.controller;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.service.MusteriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Postman'den test edildi -> get ve post çalışıyor
@RestController
@RequestMapping("/musteri")
public class MusteriController {

    private MusteriService musteriService;

    public MusteriController(MusteriService musteriService) {
        this.musteriService = musteriService;
    }

    @PostMapping
    public Musteri createMusteri(@RequestBody Musteri musteri){
        return musteriService.createMusteri(musteri);
    }

    @GetMapping
    public List<Musteri> getAllMusteri(){
        return musteriService.getAllMusteri();
    }


}
