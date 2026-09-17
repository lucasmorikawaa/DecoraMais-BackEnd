package com.decoramais.decoramais_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.Professor;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.repository.AlunoRepository;
import com.decoramais.decoramais_backend.repository.ProfessorRepository;
import com.decoramais.decoramais_backend.repository.SalaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    public SalaService(
            SalaRepository salaRepository,
            AlunoRepository alunoRepository,
            ProfessorRepository professorRepository) {

        this.salaRepository = salaRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }

    public Sala getSalaById(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Sala não encontrada com o ID: " + id));
    }

    public Sala createSala(Sala sala, Long professorId) {

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Professor não encontrado"));

        sala.setProfessor(professor);

        return salaRepository.save(sala);
    }

    public Sala updateSala(Long id, Sala salaRequest) {

        Sala existingSala = salaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sala não encontrada"));

        existingSala.setNome(salaRequest.getNome());
        existingSala.setDisciplina(salaRequest.getDisciplina());
        existingSala.setAno(salaRequest.getAno());

        return salaRepository.save(existingSala);
    }

    public void deleteSala(Long id) {

        if (!salaRepository.existsById(id)) {
            throw new EntityNotFoundException("Sala não encontrada");
        }

        salaRepository.deleteById(id);
    }

    public void vincularAluno(String codigo, Long alunoId) {

        Sala sala = salaRepository.findByCodigConvite(codigo)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Código de convite inválido"));

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aluno não encontrado"));

        if (!sala.getAlunos().contains(aluno)) {
            sala.getAlunos().add(aluno);
            salaRepository.save(sala);
        }
    }
}