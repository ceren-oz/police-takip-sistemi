package com.example.customer_management.service;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.domain.MusteriAdres;
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
    public MusteriEpostaDTO createMusteriEposta(String musteriId, MusteriEpostaDTO epostaDTO){

        MusteriEposta eposta = musteriEpostaMapper.toEntity(epostaDTO);

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found"));
        eposta.setMusteri(musteri);

        MusteriEposta saved = musteriEpostaRepository.save(eposta);
        return musteriEpostaMapper.toDTO(saved);
    }

    @Transactional
    public MusteriEpostaDTO updateMusteriEposta(String musteriId,Long id,
                                                MusteriEpostaDTO epostaDTO) {
        // Find existing email
        MusteriEposta existingEposta = musteriEpostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri e-posta not found"));

        // Update associated customer if musteriId is provided and different
        if (musteriId != null &&
                (existingEposta.getMusteri() == null ||
                        !musteriId.equals(existingEposta.getMusteri().getId()))) {

            Musteri musteri = musteriRepository.findById(musteriId)
                    .orElseThrow(() -> new RuntimeException("Musteri not found"));
            existingEposta.setMusteri(musteri);
        }

        // Update fields
        if (epostaDTO.getEpostaAdresi() != null) {
            existingEposta.setEpostaAdresi(epostaDTO.getEpostaAdresi());
        }
        if(epostaDTO.getEtkIzniVarMi() != null){
            existingEposta.setEtkIzniVarMi(epostaDTO.getEtkIzniVarMi());
        }
        if(epostaDTO.getVarsayilanMi() != null){
            existingEposta.setVarsayilanMi(epostaDTO.getVarsayilanMi());
        }

        MusteriEposta updated = musteriEpostaRepository.save(existingEposta);
        return musteriEpostaMapper.toDTO(updated);
    }

    public MusteriEpostaDTO getMusteriEpostaById(String musteriId, Long id){

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));

        MusteriEposta eposta = musteriEpostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri e-posta not found"));

        if (!eposta.getMusteri().getId().equals(musteri.getId())) {
            throw new RuntimeException("This address does not belong to the specified customer");
        }

        return musteriEpostaMapper.toDTO(eposta);
    }

    public List<MusteriEpostaDTO> getAllEpostalarByMusteriId(String musteriId) {
        return musteriEpostaRepository.findByMusteriId(musteriId)
                .stream()
                .map(musteriEpostaMapper::toDTO)
                .toList();
    }
    @Transactional
    public void deleteMusteriEposta(String musteriId, Long id) {

        Musteri musteri = musteriRepository.findById(musteriId)
                .orElseThrow(() -> new RuntimeException("Musteri not found with id: " + musteriId));

        MusteriEposta eposta = musteriEpostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musteri e-posta not found"));

        if (!eposta.getMusteri().getId().equals(musteri.getId())) {
            throw new RuntimeException("This address does not belong to the specified customer");
        }
        musteriEpostaRepository.deleteById(id);
    }
}
