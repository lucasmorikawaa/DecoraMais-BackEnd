package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.dto.usuario.AlterarSenhaDTO;
import com.decoramais.decoramais_backend.dto.usuario.AtualizarUsuarioDTO;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario atualizar(
            AtualizarUsuarioDTO dto,
            Long usuarioId
    ) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuário não encontrado"
                        )
                );

        usuarioRepository.findByEmail(dto.email())
                .ifPresent(usuarioExistente -> {

                    if (!usuarioExistente.getId().equals(usuarioId)) {
                        throw new IllegalArgumentException(
                                "O email informado já está em uso."
                        );
                    }
                });

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(
            AlterarSenhaDTO dto,
            Long usuarioId
    ) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuário não encontrado"
                        )
                );

        if (!passwordEncoder.matches(dto.senhaAtual(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        usuario.setSenha(
                passwordEncoder.encode(dto.novaSenha())
        );

        usuarioRepository.save(usuario);
    }
}