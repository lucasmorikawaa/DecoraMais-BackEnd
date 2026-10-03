package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.dto.flashcard.AvaliacaoFlashcardDTO;
import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.Flashcard;
import com.decoramais.decoramais_backend.entity.ProgressoFlashcard;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.repository.AlunoRepository;
import com.decoramais.decoramais_backend.repository.FlashcardRepository;
import com.decoramais.decoramais_backend.repository.ProgressoFlashcardRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EstudoService {

    @Autowired
    private GamificationService gamificationService;

    private final ProgressoFlashcardRepository progressoRepository;
    private final AlunoRepository alunoRepository;
    private final FlashcardRepository flashcardRepository;

    public EstudoService(
            ProgressoFlashcardRepository progressoRepository,
            AlunoRepository alunoRepository,
            FlashcardRepository flashcardRepository) {

        this.progressoRepository = progressoRepository;
        this.alunoRepository = alunoRepository;
        this.flashcardRepository = flashcardRepository;
    }

    @Transactional
    public ProgressoFlashcard processarAvaliacao(
            AvaliacaoFlashcardDTO dto,
            Long alunoId) {

        Flashcard flashcard = flashcardRepository.findById(dto.flashcardId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Flashcard não encontrado"));

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aluno não encontrado"));

        boolean possuiAcesso = flashcard.getSalas()
                .stream()
                .anyMatch(sala -> sala.getAlunos()
                        .stream()
                        .anyMatch(a -> a.getId().equals(alunoId)));

        if (!possuiAcesso) {
            throw new AccessDeniedException(
                    "Você não tem permissão para revisar este flashcard.");
        }

        ProgressoFlashcard progresso = progressoRepository
                .findByAlunoIdAndFlashcardId(
                        alunoId,
                        dto.flashcardId())
                .orElseGet(() -> inicializarNovoProgresso(
                        aluno,
                        flashcard));

        int q = dto.nota();
        double ef = progresso.getFatorFacilidade();
        int rep = progresso.getRepeticoes();
        int intervalo;

        if (q < 3) {
            rep = 0;
            intervalo = 1;
        } else {
            if (rep == 0) {
                intervalo = 1;
            } else if (rep == 1) {
                intervalo = 6;
            } else {
                intervalo = (int) Math.round(
                        progresso.getIntervaloDias() * ef);
            }

            rep++;
        }

        ef = ef + (
                0.1 -
                (5 - q) * (
                        0.08 +
                        (5 - q) * 0.02
                )
        );

        if (ef < 1.3) {
            ef = 1.3;
        }

        progresso.setRepeticoes(rep);
        progresso.setFatorFacilidade(ef);
        progresso.setIntervaloDias(intervalo);
        progresso.setDataProximaRevisao(
                LocalDate.now().plusDays(intervalo)
        );

        ProgressoFlashcard resultado =
                progressoRepository.save(progresso);

        gamificationService.processarGamificacao(
                progresso.getAluno());

        return resultado;
    }

    private ProgressoFlashcard inicializarNovoProgresso(
            Aluno aluno,
            Flashcard flashcard) {

        ProgressoFlashcard novoProgresso =
                new ProgressoFlashcard();

        novoProgresso.setAluno(aluno);
        novoProgresso.setFlashcard(flashcard);

        return novoProgresso;
    }
}