package com.decoramais.decoramais_backend.dto.flashcard;

import java.time.LocalDate;
import java.util.List;

public class FlashcardResponseDTO {

    private Long id;
    private String pergunta;
    private String resposta;
    private LocalDate dataCriacao;
    private List<Long> salaIds;

    public FlashcardResponseDTO(
            Long id,
            String pergunta,
            String resposta,
            LocalDate dataCriacao,
            List<Long> salaIds) {

        this.id = id;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.dataCriacao = dataCriacao;
        this.salaIds = salaIds;
    }

    public Long getId() {
        return id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public List<Long> getSalaIds() {
        return salaIds;
    }
}