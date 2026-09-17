package com.decoramais.decoramais_backend.dto.flashcard;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoFlashcardDTO(
    @NotNull
    Long flashcardId,
    
    @NotNull
    Long alunoId,
    
    @Min(value = 0, message = "A nota mínima é 0 (Não lembro)")
    @Max(value = 5, message = "A nota máxima é 5 (Muito fácil)")
    Integer nota
) {}