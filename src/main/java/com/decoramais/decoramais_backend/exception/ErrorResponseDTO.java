package com.decoramais.decoramais_backend.exception;

public record ErrorResponseDTO(
        int status,
        String mensagem
) {
}