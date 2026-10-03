package com.decoramais.decoramais_backend.dto.flashcard;

import java.time.LocalDate;
import java.util.List;

public class FlashcardResponseDTO {

    private Long id;
    private String pergunta;
    private String resposta;
    private LocalDate dataCriacao;
    private String imagem;
    private LocalDate dataDisponibilidade;
    private List<Long> salaIds;

    public FlashcardResponseDTO(
            Long id,
            String pergunta,
            String resposta,
            LocalDate dataCriacao,
            String imagem,
            LocalDate dataDisponibilidade,
            List<Long> salaIds) {

        this.id = id;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.dataCriacao = dataCriacao;
        this.imagem = imagem;
        this.dataDisponibilidade = dataDisponibilidade;
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

    public String getImagem() {
        return imagem;
    }

    public LocalDate getDataDisponibilidade() {
        return dataDisponibilidade;
    }

    public List<Long> getSalaIds() {
        return salaIds;
    }
}