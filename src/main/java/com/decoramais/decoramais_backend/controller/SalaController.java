package com.decoramais.decoramais_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.service.SalaService;

@RestController
@RequestMapping("/salas")
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    public List<Sala> getAllSalas() {
        return salaService.getAllSalas();
    }

    public Sala getSalaById(Long id) {
        return salaService.getSalaById(id);
    }

    public Sala createSala(Sala sala) {
        return salaService.createSala(sala);
    }

    public Sala updateSala(Long id, Sala sala) {
        return salaService.updateSala(id, sala);
    }

    public void deleteSala(Long id) {
        salaService.deleteSala(id);
    }

}
