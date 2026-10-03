package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.dto.flashcard.FlashcardRequestDTO;
import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.Flashcard;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.repository.FlashcardRepository;
import com.decoramais.decoramais_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.decoramais.decoramais_backend.entity.Professor;
import com.decoramais.decoramais_backend.entity.ProgressoFlashcard;
import com.decoramais.decoramais_backend.repository.ProfessorRepository;
import com.decoramais.decoramais_backend.repository.ProgressoFlashcardRepository;

import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
public class FlashcardService {

        private final FlashcardRepository flashcardRepository;
        private final SalaRepository salaRepository;
        private final ProfessorRepository professorRepository;
        private final ProgressoFlashcardRepository progressoFlashcardRepository;

        public FlashcardService(
                        FlashcardRepository flashcardRepository,
                        SalaRepository salaRepository,
                        ProfessorRepository professorRepository,
                        ProgressoFlashcardRepository progressoFlashcardRepository) {

                this.flashcardRepository = flashcardRepository;
                this.salaRepository = salaRepository;
                this.professorRepository = professorRepository;
                this.progressoFlashcardRepository = progressoFlashcardRepository;
        }

        @Transactional
        public Flashcard criarEDistribuir(
                        FlashcardRequestDTO dto,
                        Long professorId) {

                Professor professor = professorRepository.findById(professorId)
                                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));

                Flashcard flashcard = new Flashcard();
                flashcard.setPergunta(dto.pergunta());
                flashcard.setResposta(dto.resposta());
                flashcard.setImagem(dto.imagem());
                flashcard.setDataDisponibilidade(dto.dataDisponibilidade());

                List<Sala> salas = salaRepository.findAllById(dto.salaIds());

                if (salas.size() != dto.salaIds().size()) {
                        throw new EntityNotFoundException(
                                        "Uma ou mais salas informadas não foram encontradas.");
                }

                boolean algumaSalaDeOutroProfessor = salas.stream()
                                .anyMatch(sala -> sala.getProfessor() == null ||
                                                !sala.getProfessor().getId().equals(professor.getId()));

                if (algumaSalaDeOutroProfessor) {
                        throw new AccessDeniedException(
                                        "Você não tem permissão para utilizar uma ou mais salas informadas.");
                }

                flashcard.setSalas(salas);

                return flashcardRepository.save(flashcard);
        }

        public List<Flashcard> listarPorSala(Long salaId, Usuario usuario) {

                Sala sala = salaRepository.findById(salaId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Sala não encontrada com o ID: " + salaId));

                boolean possuiAcesso = false;

                if (usuario instanceof Professor professor) {

                        possuiAcesso = sala.getProfessor() != null
                                        && sala.getProfessor().getId().equals(professor.getId());

                } else if (usuario instanceof Aluno aluno) {

                        possuiAcesso = sala.getAlunos()
                                        .stream()
                                        .anyMatch(a -> a.getId().equals(aluno.getId()));
                }

                if (!possuiAcesso) {
                        throw new AccessDeniedException(
                                        "Você não tem permissão para visualizar os flashcards desta sala.");
                }

                return flashcardRepository.findBySalasId(salaId);
        }

        @Transactional
        public void deletar(Long id, Long professorId) {

                Flashcard flashcard = flashcardRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Flashcard não encontrado."));

                boolean professorResponsavel = flashcard.getSalas()
                                .stream()
                                .anyMatch(sala -> sala.getProfessor() != null &&
                                                sala.getProfessor().getId().equals(professorId));

                if (!professorResponsavel) {
                        throw new AccessDeniedException(
                                        "Você não tem permissão para excluir este flashcard.");
                }

                flashcardRepository.delete(flashcard);
        }

        @Transactional
        public Flashcard atualizar(
                        FlashcardRequestDTO dto,
                        Long id,
                        Long professorId) {

                Flashcard flashcard = flashcardRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Flashcard não encontrado."));

                Professor professor = professorRepository.findById(professorId)
                                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));

                List<Sala> salas = salaRepository.findAllById(dto.salaIds());

                if (salas.size() != dto.salaIds().size()) {
                        throw new EntityNotFoundException(
                                        "Uma ou mais salas informadas não foram encontradas.");
                }

                boolean algumaSalaDeOutroProfessor = salas.stream()
                                .anyMatch(sala -> sala.getProfessor() == null ||
                                                !sala.getProfessor().getId().equals(professor.getId()));

                if (algumaSalaDeOutroProfessor) {
                        throw new AccessDeniedException(
                                        "Você não tem permissão para utilizar uma ou mais salas informadas.");
                }

                flashcard.setPergunta(dto.pergunta());
                flashcard.setResposta(dto.resposta());
                flashcard.setImagem(dto.imagem());
                flashcard.setDataDisponibilidade(dto.dataDisponibilidade());
                flashcard.setSalas(salas);

                return flashcardRepository.save(flashcard);
        }

        public List<Flashcard> buscarDisponiveisParaRevisao(Long alunoId) {

                List<Flashcard> flashcards = flashcardRepository.findDisponiveisPorAluno(alunoId);

                return flashcards.stream()
                                .filter(flashcard -> {

                                        ProgressoFlashcard progresso = progressoFlashcardRepository
                                                        .findByAlunoIdAndFlashcardId(
                                                                        alunoId,
                                                                        flashcard.getId())
                                                        .orElse(null);

                                        // Nunca revisado
                                        if (progresso == null) {
                                                return true;
                                        }

                                        // Já revisado: verifica se chegou a próxima revisão
                                        return !progresso.getDataProximaRevisao()
                                                        .isAfter(java.time.LocalDate.now());
                                })
                                .toList();
        }
}