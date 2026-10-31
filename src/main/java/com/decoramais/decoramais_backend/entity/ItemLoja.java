
package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "itens_loja")
@Getter
@Setter
public class ItemLoja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private String imagem;

    @Column(nullable = false)
    private Integer custoXp;

    @Column(nullable = false)
    private Boolean ativo = true;
}
