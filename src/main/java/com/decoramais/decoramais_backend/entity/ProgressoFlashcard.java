package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
    name = "progresso_flashcards",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_aluno_flashcard",
            columnNames = {"aluno_id", "flashcard_id"}
        )
    }
)
@Getter
@Setter
public class ProgressoFlashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;

    private Integer repeticoes = 0;

    private Double fatorFacilidade = 2.5;

    private Integer intervaloDias = 0;

    private LocalDate dataProximaRevisao = LocalDate.now();

}