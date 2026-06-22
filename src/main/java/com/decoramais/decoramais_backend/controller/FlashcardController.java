package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.FlashcardRequestDTO;
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
    public ResponseEntity<Flashcard> criarFlashcard(@RequestBody @Valid FlashcardRequestDTO dto) {
        Flashcard novoFlashcard = flashcardService.criarEDistribuir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFlashcard);
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<Flashcard>> listarPorSala(@PathVariable Long salaId) {
        List<Flashcard> flashcards = flashcardService.listarPorSala(salaId);
        return ResponseEntity.ok(flashcards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFlashcard(@PathVariable Long id) {
        flashcardService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}