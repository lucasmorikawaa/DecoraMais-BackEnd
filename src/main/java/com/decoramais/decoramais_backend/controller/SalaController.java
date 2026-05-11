package com.decoramais.decoramais_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.service.SalaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/salas")
public class SalaController {
    
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<List<Sala>> getAllSalas() {
        List<Sala> salas = salaService.getAllSalas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> getSalaById(@PathVariable Long id) {
        Sala sala = salaService.getSalaById(id);
        return ResponseEntity.ok(sala);
    }

    @PostMapping
    public ResponseEntity<Sala> createSala(@RequestBody @Valid Sala sala) {
        Sala novaSala = salaService.createSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> updateSala(@PathVariable Long id, @RequestBody @Valid Sala sala) {
        Sala salaAtualizada = salaService.updateSala(id, sala);
        return ResponseEntity.ok(salaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        salaService.deleteSala(id);
        return ResponseEntity.noContent().build();
    }
}