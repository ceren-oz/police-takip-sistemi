package com.example.customer_management.controller;

import com.example.customer_management.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {
    private final LocationService svc;

    @GetMapping("/iller")
    public ResponseEntity<List<String>> iller() {
        return ResponseEntity.ok(svc.getProvinces());
    }

    // note: encodeURIComponent on client recommended
    @GetMapping("/iller/{il}/ilceler")
    public ResponseEntity<List<String>> ilceler(@PathVariable("il") String il) {
        return ResponseEntity.ok(svc.getDistricts(il));
    }
}
