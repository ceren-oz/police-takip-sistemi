package com.example.customer_management.service;

import com.example.customer_management.domain.AracBilgileri;
import com.example.customer_management.mapper.AracBilgileriDTO;
import com.example.customer_management.mapper.AracBilgileriMapper;
import com.example.customer_management.repository.AracBilgileriRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class AracBilgileriService {

    private final AracBilgileriRepository aracRepository;

    public AracBilgileriService(AracBilgileriRepository aracRepository) {
        this.aracRepository = aracRepository;
    }

    public AracBilgileriDTO create(AracBilgileriDTO dto) {
        validate(dto);
        AracBilgileri entity = AracBilgileriMapper.toEntity(dto);
        AracBilgileri saved = aracRepository.save(entity);
        return AracBilgileriMapper.toDTO(saved);
    }

    public AracBilgileriDTO update(Long id, AracBilgileriDTO dto) {
        validate(dto);
        AracBilgileri existing = aracRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + id));

        if (dto.getPlakaNo() != null) existing.setPlakaNo(dto.getPlakaNo());
        if (dto.getMarkaModel() != null) existing.setMarkaModel(dto.getMarkaModel());
        if (dto.getAracTipi() != null) existing.setAracTipi(dto.getAracTipi());
        if (dto.getModelYili() != null) existing.setModelYili(dto.getModelYili());
        if (dto.getYakitTuru() != null) existing.setYakitTuru(dto.getYakitTuru());
        if (dto.getAracDegeri() != null) existing.setAracDegeri(dto.getAracDegeri());

        return AracBilgileriMapper.toDTO(aracRepository.save(existing));
    }

    public void delete(Long id) {
        if (!aracRepository.existsById(id)) {
            throw new EntityNotFoundException("Araç bulunamadı: " + id);
        }
        aracRepository.deleteById(id);
    }

    public List<AracBilgileriDTO> getAll() {
        return aracRepository.findAll()
                .stream()
                .map(AracBilgileriMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AracBilgileriDTO getById(Long id) {
        return aracRepository.findById(id)
                .map(AracBilgileriMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Araç bulunamadı: " + id));
    }

    private void validate(AracBilgileriDTO dto) {
        if (dto.getPlakaNo() == null || !Pattern.matches("^[0-9]{2}[A-Z]{1,3}[0-9]{2,4}$", dto.getPlakaNo())) {
            throw new IllegalArgumentException("Geçerli bir plaka numarası giriniz! (örn: 34ABC123)");
        }
        if (dto.getMarkaModel() == null || dto.getMarkaModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Marka/Model boş olamaz!");
        }
        if (dto.getModelYili() != null && (dto.getModelYili() < 1950 || dto.getModelYili() > 2100)) {
            throw new IllegalArgumentException("Geçersiz model yılı!");
        }
    }
}
