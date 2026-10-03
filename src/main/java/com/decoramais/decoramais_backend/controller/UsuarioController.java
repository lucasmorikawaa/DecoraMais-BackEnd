package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.usuario.AlterarSenhaDTO;
import com.decoramais.decoramais_backend.dto.usuario.AtualizarUsuarioDTO;
import com.decoramais.decoramais_backend.dto.usuario.UsuarioResponseDTO;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> buscarMeuPerfil(
            Authentication authentication
    ) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        UsuarioResponseDTO resposta =
                new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTipo()
                );

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @RequestBody @Valid AtualizarUsuarioDTO dto,
            Authentication authentication
    ) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Usuario usuarioAtualizado =
                usuarioService.atualizar(
                        dto,
                        usuario.getId()
                );

        UsuarioResponseDTO resposta =
                new UsuarioResponseDTO(
                        usuarioAtualizado.getId(),
                        usuarioAtualizado.getNome(),
                        usuarioAtualizado.getEmail(),
                        usuarioAtualizado.getTipo()
                );

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/senha")
    public ResponseEntity<Void> alterarSenha(
            @RequestBody @Valid AlterarSenhaDTO dto,
            Authentication authentication
    ) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        usuarioService.alterarSenha(dto, usuario.getId());

        return ResponseEntity.noContent().build();
    }
}