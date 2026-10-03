package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.dto.flashcard.CardDificilDTO;
import com.decoramais.decoramais_backend.entity.ProgressoFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressoFlashcardRepository extends JpaRepository<ProgressoFlashcard, Long> {
    
    Optional<ProgressoFlashcard> findByAlunoIdAndFlashcardId(Long alunoId, Long flashcardId);

    
    @Query("SELECT AVG(p.fatorFacilidade) FROM ProgressoFlashcard p " +
           "JOIN p.flashcard f " +
           "JOIN f.salas s " +
           "WHERE s.id = :salaId")
    Double findMediaFatorFacilidadePorSala(@Param("salaId") Long salaId);

    @Query("SELECT new com.decoramais.decoramais_backend.dto.flashcard.CardDificilDTO(f.id, f.pergunta, AVG(p.fatorFacilidade)) " +
           "FROM ProgressoFlashcard p " +
           "JOIN p.flashcard f " +
           "JOIN f.salas s " +
           "WHERE s.id = :salaId " +
           "GROUP BY f.id, f.pergunta " +
           "ORDER BY AVG(p.fatorFacilidade) ASC")
    List<CardDificilDTO> findCardsMaisDificeisPorSala(@Param("salaId") Long salaId);
}