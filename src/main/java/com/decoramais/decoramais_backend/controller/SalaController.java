package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.sala.SalaRequestDTO;
import com.decoramais.decoramais_backend.dto.sala.SalaResponseDTO;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.decoramais.decoramais_backend.entity.Usuario;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> getAllSalas() {

        List<SalaResponseDTO> salas = salaService.getAllSalas()
                .stream()
                .map(this::toResponseDTO)
                .toList();

        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> getSalaById(@PathVariable Long id) {

        Sala sala = salaService.getSalaById(id);

        return ResponseEntity.ok(toResponseDTO(sala));
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> criarSala(
            @RequestBody @Valid SalaRequestDTO dados,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Sala novaSala = new Sala();

        novaSala.setNome(dados.nome());
        novaSala.setDisciplina(dados.disciplina());
        novaSala.setAno(dados.ano());

        Sala salaSalva = salaService.createSala(novaSala, usuario.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponseDTO(salaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> updateSala(
            @PathVariable Long id,
            @RequestBody @Valid SalaRequestDTO dados,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Sala salaRequest = new Sala();

        salaRequest.setNome(dados.nome());
        salaRequest.setDisciplina(dados.disciplina());
        salaRequest.setAno(dados.ano());

        Sala salaAtualizada = salaService.updateSala(
                id,
                salaRequest,
                usuario.getId());

        return ResponseEntity.ok(toResponseDTO(salaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSala(
            @PathVariable Long id,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        salaService.deleteSala(id, usuario.getId());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ingressar")
    public ResponseEntity<String> ingressarNaSala(
            @RequestParam String codigo,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        salaService.vincularAluno(codigo, usuario.getId());

        return ResponseEntity.ok("Aluno vinculado com sucesso à sala!");
    }

    private SalaResponseDTO toResponseDTO(Sala sala) {

        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getDisciplina(),
                sala.getAno(),
                sala.getCodigConvite());
    }
}