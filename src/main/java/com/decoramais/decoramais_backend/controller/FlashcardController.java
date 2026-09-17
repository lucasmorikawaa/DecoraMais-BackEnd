package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.flashcard.FlashcardRequestDTO;
import com.decoramais.decoramais_backend.dto.flashcard.FlashcardResponseDTO;
import com.decoramais.decoramais_backend.entity.Flashcard;
import com.decoramais.decoramais_backend.service.FlashcardService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public ResponseEntity<FlashcardResponseDTO> criarFlashcard(
            @RequestBody @Valid FlashcardRequestDTO dto) {

        Flashcard novoFlashcard = flashcardService.criarEDistribuir(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponseDTO(novoFlashcard));
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<FlashcardResponseDTO>> listarPorSala(
            @PathVariable Long salaId) {

        List<FlashcardResponseDTO> flashcards = flashcardService
                .listarPorSala(salaId)
                .stream()
                .map(this::toResponseDTO)
                .toList();

        return ResponseEntity.ok(flashcards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFlashcard(@PathVariable Long id) {

        flashcardService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    private FlashcardResponseDTO toResponseDTO(Flashcard flashcard) {

        List<Long> salaIds = flashcard.getSalas()
                .stream()
                .map(sala -> sala.getId())
                .toList();

        return new FlashcardResponseDTO(
                flashcard.getId(),
                flashcard.getPergunta(),
                flashcard.getResposta(),
                flashcard.getDataCriacao(),
                salaIds
        );
    }
}