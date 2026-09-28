package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.flashcard.AvaliacaoFlashcardDTO;
import com.decoramais.decoramais_backend.entity.ProgressoFlashcard;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.service.EstudoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudo")
public class EstudoController {

    private final EstudoService estudoService;

    public EstudoController(EstudoService estudoService) {
        this.estudoService = estudoService;
    }

    @PostMapping("/revisar")
    public ResponseEntity<ProgressoFlashcard> revisarCard(
            @RequestBody @Valid AvaliacaoFlashcardDTO dto,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        ProgressoFlashcard progressoAtualizado =
                estudoService.processarAvaliacao(dto, usuario.getId());

        return ResponseEntity.ok(progressoAtualizado);
    }
}