package com.decoramais.decoramais_backend.dto.usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String tipo
) {
}
