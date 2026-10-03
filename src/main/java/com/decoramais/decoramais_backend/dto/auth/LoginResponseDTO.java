package com.decoramais.decoramais_backend.dto.auth;

import com.decoramais.decoramais_backend.dto.usuario.UsuarioResponseDTO;

public record LoginResponseDTO(
        String token,
        UsuarioResponseDTO usuario
) {
}