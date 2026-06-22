package com.decoramais.decoramais_backend.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alunos")
@Getter
@Setter
public class Aluno extends Usuario {

    @ManyToMany(mappedBy = "alunos")
    private List<Sala> salas;

    private Integer xp = 0;
    private Integer ofensiva = 0;
    private java.time.LocalDate dataUltimoEstudo;
    
}
