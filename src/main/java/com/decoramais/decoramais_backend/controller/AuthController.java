package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.auth.LoginRequestDTO;
import com.decoramais.decoramais_backend.dto.auth.LoginResponseDTO;
import com.decoramais.decoramais_backend.dto.auth.RegisterRequestDTO;
import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.Professor;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.security.TokenService;
import com.decoramais.decoramais_backend.repository.UsuarioRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO dados
    ) {

        var authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        dados.email(),
                        dados.senha()
                );

        var authentication =
                authenticationManager.authenticate(authenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(
                new LoginResponseDTO(token)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequestDTO dados
    ) {

        if (usuarioRepository.existsByEmail(dados.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Usuario usuario;

        if (dados.tipo().equalsIgnoreCase("PROFESSOR")) {

            usuario = new Professor();

        } else if (dados.tipo().equalsIgnoreCase("ALUNO")) {

            usuario = new Aluno();

        } else {

            return ResponseEntity.badRequest().build();
        }

        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuario.setTipo(dados.tipo().toUpperCase());

        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}