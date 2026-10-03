package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.flashcard.FlashcardRequestDTO;
import com.decoramais.decoramais_backend.dto.flashcard.FlashcardResponseDTO;
import com.decoramais.decoramais_backend.entity.Flashcard;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.service.FlashcardService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
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
                        @RequestBody @Valid FlashcardRequestDTO dto,
                        Authentication authentication) {

                Usuario usuario = (Usuario) authentication.getPrincipal();

                Flashcard novoFlashcard = flashcardService
                                .criarEDistribuir(dto, usuario.getId());

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(toResponseDTO(novoFlashcard));
        }

        @GetMapping("/sala/{salaId}")
        public ResponseEntity<List<FlashcardResponseDTO>> listarPorSala(
                        @PathVariable Long salaId,
                        Authentication authentication) {

                Usuario usuario = (Usuario) authentication.getPrincipal();

                List<FlashcardResponseDTO> flashcards = flashcardService
                                .listarPorSala(salaId, usuario)
                                .stream()
                                .map(this::toResponseDTO)
                                .toList();

                return ResponseEntity.ok(flashcards);
        }

        @PutMapping("/{id}")
        public ResponseEntity<FlashcardResponseDTO> atualizarFlashcard(
                        @PathVariable Long id,
                        @RequestBody @Valid FlashcardRequestDTO dto,
                        Authentication authentication) {

                Usuario usuario = (Usuario) authentication.getPrincipal();

                Flashcard flashcardAtualizado = flashcardService
                                .atualizar(dto, id, usuario.getId());

                return ResponseEntity.ok(toResponseDTO(flashcardAtualizado));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarFlashcard(
                        @PathVariable Long id,
                        Authentication authentication) {

                Usuario usuario = (Usuario) authentication.getPrincipal();

                flashcardService.deletar(id, usuario.getId());

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
                                flashcard.getImagem(),
                                flashcard.getDataDisponibilidade(),
                                salaIds);
        }

        @GetMapping("/revisao")
        public ResponseEntity<List<FlashcardResponseDTO>> buscarDisponiveisParaRevisao(
                        Authentication authentication) {

                Usuario usuario = (Usuario) authentication.getPrincipal();

                List<FlashcardResponseDTO> flashcards = flashcardService.buscarDisponiveisParaRevisao(usuario.getId())
                                .stream()
                                .map(this::toResponseDTO)
                                .toList();

                return ResponseEntity.ok(flashcards);
        }
}