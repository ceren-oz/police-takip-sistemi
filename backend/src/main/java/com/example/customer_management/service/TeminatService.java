package com.example.customer_management.service;

import com.example.customer_management.domain.Teminat;
import com.example.customer_management.mapper.TeminatDTO;
import com.example.customer_management.mapper.TeminatMapper;
import com.example.customer_management.repository.TeminatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeminatService {

    private final TeminatRepository teminatRepository;

    public TeminatService(TeminatRepository teminatRepository) {
        this.teminatRepository = teminatRepository;
    }

    public TeminatDTO create(TeminatDTO dto) {
        validate(dto);
        Teminat entity = TeminatMapper.toEntity(dto);
        Teminat saved = teminatRepository.save(entity);
        return TeminatMapper.toDTO(saved);
    }

    public TeminatDTO update(Long id, TeminatDTO dto) {
        validate(dto);
        Teminat existing = teminatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teminat bulunamadı: " + id));

        if (dto.getTeminatTuru() != null) existing.setTeminatTuru(dto.getTeminatTuru());
        if (dto.getTeminatBedeli() != null) existing.setTeminatBedeli(dto.getTeminatBedeli());
        existing.setGuncellemeTarihi(dto.getGuncellemeTarihi());

        return TeminatMapper.toDTO(teminatRepository.save(existing));
    }

    public void delete(Long id) {
        if (!teminatRepository.existsById(id)) {
            throw new EntityNotFoundException("Teminat bulunamadı: " + id);
        }
        teminatRepository.deleteById(id);
    }

    public List<TeminatDTO> getAll() {
        return teminatRepository.findAll()
                .stream()
                .map(TeminatMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TeminatDTO getById(Long id) {
        return teminatRepository.findById(id)
                .map(TeminatMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Teminat bulunamadı: " + id));
    }

    private void validate(TeminatDTO dto) {
        if (dto.getTeminatTuru() == null) {
            throw new IllegalArgumentException("Teminat türü boş olamaz!");
        }
        if (dto.getTeminatBedeli() == null || dto.getTeminatBedeli().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Teminat bedeli sıfırdan büyük olmalıdır!");
        }
    }
}
