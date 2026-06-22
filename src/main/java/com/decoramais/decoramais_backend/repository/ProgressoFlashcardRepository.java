package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.entity.ProgressoFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProgressoFlashcardRepository extends JpaRepository<ProgressoFlashcard, Long> {
    Optional<ProgressoFlashcard> findByAlunoIdAndFlashcardId(Long alunoId, Long flashcardId);
}