package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.dto.flashcard.CardDificilDTO;
import com.decoramais.decoramais_backend.dto.RelatorioTurmaDTO;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.repository.ProgressoFlashcardRepository;
import com.decoramais.decoramais_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
public class RelatorioService {

    private final SalaRepository salaRepository;
    private final ProgressoFlashcardRepository progressoRepository;

    public RelatorioService(SalaRepository salaRepository, ProgressoFlashcardRepository progressoRepository) {
        this.salaRepository = salaRepository;
        this.progressoRepository = progressoRepository;
    }

    public RelatorioTurmaDTO gerarRelatorioDaTurma(
            Long salaId,
            Long professorId) {

        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sala não encontrada com o ID: " + salaId));

        if (sala.getProfessor() == null
                || !sala.getProfessor().getId().equals(professorId)) {

            throw new AccessDeniedException(
                    "Você não tem permissão para acessar o relatório desta sala.");
        }

        Integer totalAlunos = sala.getAlunos() != null
                ? sala.getAlunos().size()
                : 0;

        Double mediaFacilidade = progressoRepository.findMediaFatorFacilidadePorSala(salaId);

        if (mediaFacilidade == null) {
            mediaFacilidade = 0.0;
        }

        List<CardDificilDTO> cardsDificeis = progressoRepository.findCardsMaisDificeisPorSala(salaId);

        if (cardsDificeis.size() > 5) {
            cardsDificeis = cardsDificeis.subList(0, 5);
        }

        return new RelatorioTurmaDTO(
                sala.getId(),
                sala.getNome(),
                totalAlunos,
                Math.round(mediaFacilidade * 100.0) / 100.0,
                cardsDificeis);
    }
}