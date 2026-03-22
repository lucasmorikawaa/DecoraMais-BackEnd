package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable =false)
    private Long id;

    @NotBlank
    @Column(name = "nome", nullable =false)
    private String nome;

    @NotBlank    
    @Column(name = "email", nullable =false, unique =true)
    private String email;

    @NotBlank
    @Column(name = "senha", nullable =false)
    private String senha;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    
}
