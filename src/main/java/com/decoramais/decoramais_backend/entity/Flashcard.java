package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flashcards")
@Getter
@Setter
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A pergunta do flashcard é obrigatória")
    @Column(columnDefinition = "TEXT")
    private String pergunta;

    @NotBlank(message = "A resposta do flashcard é obrigatória")
    @Column(columnDefinition = "TEXT")
    private String resposta;

    private LocalDate dataCriacao;

    @ManyToMany
    @JoinTable(name = "flashcard_salas", joinColumns = @JoinColumn(name = "flashcard_id"), inverseJoinColumns = @JoinColumn(name = "sala_id"))
    private List<Sala> salas = new ArrayList<>();

    @OneToMany(mappedBy = "flashcard")
    private List<ProgressoFlashcard> progressos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDate.now();
    }
}