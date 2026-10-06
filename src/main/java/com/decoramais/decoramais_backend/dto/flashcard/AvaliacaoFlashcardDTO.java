package com.decoramais.decoramais_backend.dto.flashcard;

import com.decoramais.decoramais_backend.entity.DificuldadeFlashcard;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoFlashcardDTO(

        @NotNull(message = "O ID do flashcard é obrigatório")
        Long flashcardId,

        @NotNull(message = "A dificuldade é obrigatória")
        DificuldadeFlashcard dificuldade

) {
}