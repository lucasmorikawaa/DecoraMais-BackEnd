package com.decoramais.decoramais_backend.dto.loja;

public record ItemLojaResponseDTO(
        Long id,
        String nome,
        String descricao,
        String imagem,
        Integer custoXp
) {
}