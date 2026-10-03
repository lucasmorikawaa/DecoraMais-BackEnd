package com.decoramais.decoramais_backend.config;

import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.Professor;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria usuários de teste ao subir a aplicação, usando o MESMO
 * PasswordEncoder (BCrypt) utilizado no login/registro real.
 *
 * Substitui o antigo data.sql, que criava tabelas (USUARIO/ALUNO/PROFESSOR,
 * coluna "cargo") diferentes das geradas pelo Hibernate a partir das
 * entidades (usuarios/alunos/professores, coluna "tipo"), e gravava a senha
 * em texto puro — o que nunca bate com BCryptPasswordEncoder.matches(...).
 *
 * Credenciais de teste criadas:
 *   Professor -> carlos@decoramais.com / 123456
 *   Aluno     -> joao@decoramais.com   / 123456
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!usuarioRepository.existsByEmail("carlos@decoramais.com")) {

            Usuario professor = new Professor();
            professor.setNome("Professor Carlos");
            professor.setEmail("carlos@decoramais.com");
            professor.setSenha(passwordEncoder.encode("123456"));
            professor.setTipo("PROFESSOR");

            usuarioRepository.save(professor);
        }

        if (!usuarioRepository.existsByEmail("joao@decoramais.com")) {

            Usuario aluno = new Aluno();
            aluno.setNome("Aluno João");
            aluno.setEmail("joao@decoramais.com");
            aluno.setSenha(passwordEncoder.encode("123456"));
            aluno.setTipo("ALUNO");

            usuarioRepository.save(aluno);
        }
    }
}
