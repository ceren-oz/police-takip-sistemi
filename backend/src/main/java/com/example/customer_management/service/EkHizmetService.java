package com.example.customer_management.service;

import com.example.customer_management.domain.EkHizmet;
import com.example.customer_management.domain.Police;
import com.example.customer_management.mapper.EkHizmetDTO;
import com.example.customer_management.mapper.EkHizmetMapper;
import com.example.customer_management.mapper.PoliceDTO;
import com.example.customer_management.repository.EkHizmetRepository;
import com.example.customer_management.repository.PoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EkHizmetService {


    private final EkHizmetRepository ekHizmetRepository;
    private final PoliceRepository policeRepository;

    @Autowired
    public EkHizmetService(EkHizmetRepository ekHizmetRepository,
                           PoliceRepository policeRepository) {
        this.ekHizmetRepository = ekHizmetRepository;
        this.policeRepository = policeRepository;
    }


    public EkHizmetDTO createEkHizmet(EkHizmetDTO dto) {
        EkHizmet ekHizmet = new EkHizmet();
        ekHizmet.setHizmetTuru(dto.getHizmetTuru());
        ekHizmet.setHizmetBedeli(dto.getHizmetBedeli());

        // If policeIds are provided, fetch police entities and set them
        if (dto.getPoliceIds() != null && !dto.getPoliceIds().isEmpty()) {
            var policeSet = dto.getPoliceIds().stream()
                    .map(id -> policeRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Police not found with ID: " + id)))
                    .collect(Collectors.toSet());
            ekHizmet.setPoliceler(policeSet);

            for (Police p : policeSet) {
                if (p.getEkHizmetler() == null) {
                    p.setEkHizmetler(new HashSet<>());
                }
                p.getEkHizmetler().add(ekHizmet);
            }

        }

        EkHizmet saved = ekHizmetRepository.save(ekHizmet);

        // Map back to DTO
        EkHizmetDTO result = new EkHizmetDTO();
        result.setId(saved.getId());
        result.setHizmetTuru(saved.getHizmetTuru());
        result.setHizmetBedeli(saved.getHizmetBedeli());
        if (saved.getPoliceler() != null) {
            Set<Long> policeIds = saved.getPoliceler().stream().map(p -> p.getId()).collect(Collectors.toSet());
            result.setPoliceIds(policeIds);
        }
        return result;
    }

    // Get all EkHizmet
    public List<EkHizmetDTO> getAllEkHizmetler() {
        return ekHizmetRepository.findAll().stream().map(e -> {
            EkHizmetDTO dto = new EkHizmetDTO();
            dto.setId(e.getId());
            dto.setHizmetTuru(e.getHizmetTuru());
            dto.setHizmetBedeli(e.getHizmetBedeli());
            if (e.getPoliceler() != null) {
                Set<Long> policeIds = e.getPoliceler().stream().map(p -> p.getId()).collect(Collectors.toSet());
                dto.setPoliceIds(policeIds);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public EkHizmetDTO getByEkHizmetId(Long id) {
        EkHizmet e = ekHizmetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EkHizmet not found"));
        EkHizmetDTO dto = new EkHizmetDTO();
        dto.setId(e.getId());
        dto.setHizmetTuru(e.getHizmetTuru());
        dto.setHizmetBedeli(e.getHizmetBedeli());
        return dto;
    }

    public List<EkHizmetDTO> getEkHizmetlerByPoliceId(Long policeId) {
        return ekHizmetRepository.findAllByPoliceId(policeId).stream().map(e -> {
            EkHizmetDTO dto = new EkHizmetDTO();
            dto.setId(e.getId());
            dto.setHizmetTuru(e.getHizmetTuru());
            dto.setHizmetBedeli(e.getHizmetBedeli());
            return dto;
        }).collect(Collectors.toList());
    }

    public EkHizmetDTO updateEkHizmet(Long id, EkHizmetDTO dto) {
        EkHizmet ekHizmet = ekHizmetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EkHizmet not found"));

        ekHizmet.setHizmetTuru(dto.getHizmetTuru());
        ekHizmet.setHizmetBedeli(dto.getHizmetBedeli());

        // Önce eski ilişkileri kaldır
        if (ekHizmet.getPoliceler() != null) {
            for (Police p : ekHizmet.getPoliceler()) {
                p.getEkHizmetler().remove(ekHizmet);
            }
        }

        // Yeni poliseleri set et (if ile)
        Set<Police> policeSet = new HashSet<>();
        if (dto.getPoliceIds() != null && !dto.getPoliceIds().isEmpty()) {
            for (Long pid : dto.getPoliceIds()) {
                Police p = policeRepository.findById(pid)
                        .orElseThrow(() -> new RuntimeException("Police not found with ID: " + pid));
                policeSet.add(p);
            }
        }

        ekHizmet.setPoliceler(policeSet);

        // İki taraflı ilişkiyi güncelle
        for (Police p : policeSet) {
            if (p.getEkHizmetler() == null) {
                p.setEkHizmetler(new HashSet<>());
            }
            p.getEkHizmetler().add(ekHizmet);
        }

        EkHizmet updated = ekHizmetRepository.save(ekHizmet);

        // DTO'ya map et
        EkHizmetDTO result = new EkHizmetDTO();
        result.setId(updated.getId());
        result.setHizmetTuru(updated.getHizmetTuru());
        result.setHizmetBedeli(updated.getHizmetBedeli());
        if (updated.getPoliceler() != null) {
            Set<Long> policeIds = new HashSet<>();
            for (Police p : updated.getPoliceler()) {
                policeIds.add(p.getId());
            }
            result.setPoliceIds(policeIds);
        }

        return result;
    }


    public void deleteEkHizmet(Long id) {
        EkHizmet ekHizmet = ekHizmetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EkHizmet not found"));

        // İlişkili tüm poliselerden ekHizmeti kaldır
        if (ekHizmet.getPoliceler() != null) {
            for (Police p : ekHizmet.getPoliceler()) {
                if (p.getEkHizmetler() != null) {
                    p.getEkHizmetler().remove(ekHizmet);
                }
            }
        }

        // EkHizmeti sil
        ekHizmetRepository.delete(ekHizmet);
    }


}
