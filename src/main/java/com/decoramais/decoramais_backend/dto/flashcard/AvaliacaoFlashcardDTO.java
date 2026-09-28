package com.decoramais.decoramais_backend.dto.flashcard;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoFlashcardDTO(

    @NotNull(message = "O ID do flashcard é obrigatório")
    Long flashcardId,

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 0, message = "A nota mínima é 0 (Não lembro)")
    @Max(value = 5, message = "A nota máxima é 5 (Muito fácil)")
    Integer nota

) {}