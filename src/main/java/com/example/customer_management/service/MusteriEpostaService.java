package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriEposta;
import com.example.customer_management.mapper.MusteriEpostaDTO;
import com.example.customer_management.mapper.MusteriEpostaMapper;
import com.example.customer_management.repository.MusteriEpostaRepository;
import com.example.customer_management.repository.MusteriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusteriEpostaService {

    private final MusteriEpostaRepository musteriEpostaRepository;
    private final MusteriRepository musteriRepository;
    private final MusteriEpostaMapper musteriEpostaMapper;

    @Autowired
    public MusteriEpostaService(MusteriEpostaRepository musteriEpostaRepository,
                                MusteriRepository musteriRepository,
                                MusteriEpostaMapper musteriEpostaMapper) {
        this.musteriEpostaRepository = musteriEpostaRepository;
        this.musteriRepository = musteriRepository;
        this.musteriEpostaMapper = musteriEpostaMapper;
    }

    @Transactional
    public MusteriEpostaDTO createMusteriEposta(MusteriEpostaDTO epostaDTO){

        MusteriEposta eposta = musteriEpostaMapper.toEntity(epostaDTO);

        Musteri musteri = musteriRepository.findById(epostaDTO.getMusteriId())
                .orElseThrow(() -> new RuntimeException("Musteri not found"));
        eposta.setMusteri(musteri);

        MusteriEposta saved = musteriEpostaRepository.save(eposta);
        return musteriEpostaMapper.toDTO(saved);
    }

    public MusteriEpostaDTO getMusteriEpostaById(Long id){

        MusteriEposta eposta = musteriEpostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri e-posta not found"));
        return musteriEpostaMapper.toDTO(eposta);
    }

    public List<MusteriEpostaDTO> getAllEpostalarByMusteriId(String musteriId) {
        return musteriEpostaRepository.findByMusteriId(musteriId)
                .stream()
                .map(musteriEpostaMapper::toDTO)
                .toList();
    }
    @Transactional
    public void deleteMusteriEposta(Long id) {
        if (!musteriEpostaRepository.existsById(id)) {
            throw new RuntimeException("MusteriEposta not found");
        }
        musteriEpostaRepository.deleteById(id);
    }
}
