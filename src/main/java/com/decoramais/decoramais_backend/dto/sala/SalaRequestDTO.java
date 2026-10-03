package com.decoramais.decoramais_backend.dto.sala;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequestDTO(

        @NotBlank(message = "O nome da sala é obrigatório")
        String nome,

        @NotBlank(message = "A disciplina é obrigatória")
        String disciplina,

        @NotNull(message = "O ano é obrigatório")
        Integer ano

) {
}