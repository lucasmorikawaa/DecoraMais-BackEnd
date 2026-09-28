package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findBySalasId(Long salaId);

    @Query("""
        SELECT DISTINCT f
        FROM Flashcard f
        JOIN f.salas s
        JOIN s.alunos a
        WHERE a.id = :alunoId
        AND (f.dataDisponibilidade IS NULL OR f.dataDisponibilidade <= CURRENT_DATE)
    """)
    List<Flashcard> findDisponiveisPorAluno(@Param("alunoId") Long alunoId);
}