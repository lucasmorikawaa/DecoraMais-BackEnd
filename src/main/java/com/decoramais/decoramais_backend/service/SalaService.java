package com.decoramais.decoramais_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.repository.SalaRepository;

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
        return salaRepository.findById(id).orElse(null);
    }

    public Sala createSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala updateSala(Long id, Sala sala) {
        Sala existingSala = salaRepository.findById(id).orElse(null);
        if (existingSala != null) {
            existingSala.setNome(sala.getNome());
            return salaRepository.save(existingSala);
        }
        return null;
    }

    public void deleteSala(Long id) {
        salaRepository.deleteById(id);
    }
}
