package com.decoramais.decoramais_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.service.SalaService;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sala> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Sala> criar(@RequestBody Sala sala) {
        Sala novaSala = service.criarSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarSala(id);
        return ResponseEntity.noContent().build();
    }
}