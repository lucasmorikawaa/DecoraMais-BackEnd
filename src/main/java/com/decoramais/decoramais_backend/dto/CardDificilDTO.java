package com.decoramais.decoramais_backend.dto;

public record CardDificilDTO(
    Long cardId,
    String pergunta,
    Double notaMedia
) {}