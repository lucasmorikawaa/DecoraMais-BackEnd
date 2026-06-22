package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findBySalasId(Long salaId);
}