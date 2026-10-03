package com.decoramais.decoramais_backend.dto.flashcard;

public record CardDificilDTO(
    Long cardId,
    String pergunta,
    Double notaMedia
) {}