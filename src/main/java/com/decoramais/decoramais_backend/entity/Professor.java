package com.decoramais.decoramais_backend.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "professores")
@Getter
@Setter
public class Professor extends Usuario {

    @OneToMany(mappedBy = "professor")
    private List<Sala> salas;
    
}
