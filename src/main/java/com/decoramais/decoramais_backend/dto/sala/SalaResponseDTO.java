package com.decoramais.decoramais_backend.dto.sala;

public record SalaResponseDTO(
        Long id,
        String nome,
        String disciplina,
        Integer ano,
        String codigConvite
) {
}