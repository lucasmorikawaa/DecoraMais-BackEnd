package com.decoramais.decoramais_backend.dto;

import java.util.List;

public record RelatorioTurmaDTO(
    Long salaId,
    String nomeSala,
    Integer totalAlunos,
    Double mediaFatorFacilidade,
    List<CardDificilDTO> cardsMaisDificeis
) {}
