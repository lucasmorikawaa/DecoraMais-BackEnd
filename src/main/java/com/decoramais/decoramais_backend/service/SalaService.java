package com.decoramais.decoramais_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }

    public Sala getSalaById(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada com o ID: " + id));
    }

    public Sala createSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala updateSala(Long id, Sala salaRequest) {
        Sala existingSala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        existingSala.setNome(salaRequest.getNome());
        existingSala.setDisciplina(salaRequest.getDisciplina());
        existingSala.setAno(salaRequest.getAno());

        return salaRepository.save(existingSala);
    }

    public void deleteSala(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new EntityNotFoundException("Sala não encontrada");
        }
        salaRepository.deleteById(id);
    }
}