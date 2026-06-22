package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.dto.FlashcardRequestDTO;
import com.decoramais.decoramais_backend.entity.Flashcard;
import com.decoramais.decoramais_backend.entity.Sala;
import com.decoramais.decoramais_backend.repository.FlashcardRepository;
import com.decoramais.decoramais_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final SalaRepository salaRepository;

    public FlashcardService(FlashcardRepository flashcardRepository, SalaRepository salaRepository) {
        this.flashcardRepository = flashcardRepository;
        this.salaRepository = salaRepository;
    }

    @Transactional
    public Flashcard criarEDistribuir(FlashcardRequestDTO dto) {
        Flashcard flashcard = new Flashcard();
        flashcard.setPergunta(dto.pergunta());
        flashcard.setResposta(dto.resposta());

        // Busca todas as salas passadas no DTO
        List<Sala> salas = salaRepository.findAllById(dto.salaIds());
        if (salas.size() != dto.salaIds().size()) {
            throw new EntityNotFoundException("Uma ou mais salas informadas não foram encontradas.");
        }

        flashcard.setSalas(salas);
        return flashcardRepository.save(flashcard);
    }

    public List<Flashcard> listarPorSala(Long salaId) {
        if (!salaRepository.existsById(salaId)) {
            throw new EntityNotFoundException("Sala não encontrada com o ID: " + salaId);
        }
        return flashcardRepository.findBySalasId(salaId);
    }

    @Transactional
    public void deletar(Long id) {
        if (!flashcardRepository.existsById(id)) {
            throw new EntityNotFoundException("Flashcard não encontrado.");
        }
        flashcardRepository.deleteById(id);
    }
}