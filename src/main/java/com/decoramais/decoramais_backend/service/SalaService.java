package com.decoramais.decoramais_backend.service;

import org.springframework.stereotype.Service;

import com.decoramais.decoramais_backend.dto.SalaRepository;
import com.decoramais.decoramais_backend.entity.Sala;

import java.util.List;
import java.util.UUID;

@Service
public class SalaService {

    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    public List<Sala> listarTodas() {
        return repository.findAll();
    }

    public Sala criarSala(Sala sala) {
        String hash = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        sala.setCodigoConvite(hash);
        
        return repository.save(sala);
    }

    public void deletarSala(Long id) {
        repository.deleteById(id);
    }
}
