package com.decoramais.decoramais_backend.dto.revisao;

import java.time.LocalDate;

public record AvaliacaoFlashcardResponseDTO(
        Long flashcardId,
        int repeticoes,
        double fatorFacilidade,
        int intervaloDias,
        LocalDate dataProximaRevisao
) {}    