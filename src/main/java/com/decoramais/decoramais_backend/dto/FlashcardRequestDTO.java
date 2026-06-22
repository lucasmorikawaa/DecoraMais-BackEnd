package com.decoramais.decoramais_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record FlashcardRequestDTO(
    @NotBlank(message = "A pergunta do flashcard é obrigatória")
    String pergunta,
    
    @NotBlank(message = "A resposta do flashcard é obrigatória")
    String resposta,
    
    @NotEmpty(message = "O flashcard deve ser vinculado a pelo menos uma sala")
    List<Long> salaIds
) {}