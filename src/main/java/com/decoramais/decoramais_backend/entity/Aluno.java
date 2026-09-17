package com.decoramais.decoramais_backend.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alunos")
@Getter
@Setter
public class Aluno extends Usuario {

    @ManyToMany(mappedBy = "alunos")
    private List<Sala> salas = new ArrayList<>();

    @OneToMany(mappedBy = "aluno")
    private List<ProgressoFlashcard> progressos = new ArrayList<>();

    private Integer xp = 0;

    private Integer ofensiva = 0;

    private LocalDate dataUltimoEstudo;

}