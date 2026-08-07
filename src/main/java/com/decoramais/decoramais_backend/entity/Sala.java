package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "Sala")
@Entity
@Getter
@Setter
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "O nome da sala não pode estar em branco")
    private String nome;
    private Integer ano;
    @NotBlank(message = "A disciplina é obrigatória")
    private String disciplina;

    @Column(unique = true)
    private String codigConvite;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @PrePersist
    public void gerarCodigo() {
        if (this.codigConvite == null) {
            this.codigConvite = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        }
    }

    @ManyToMany
    @JoinTable(
        name = "sala_alunos", 
        joinColumns = @JoinColumn(name = "sala_id"), 
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;

}
