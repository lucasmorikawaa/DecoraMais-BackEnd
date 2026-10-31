package com.decoramais.decoramais_backend.dto.loja;

import java.time.LocalDateTime;

public record CompraItemResponseDTO(
        Long compraId,
        Long itemId,
        String nome,
        String imagem,
        Integer custoXp,
        LocalDateTime dataCompra,
        Integer xpRestante
) {
}