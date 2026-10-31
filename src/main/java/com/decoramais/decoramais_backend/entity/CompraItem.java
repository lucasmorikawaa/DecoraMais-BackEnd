
package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "compras_itens",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_compra_aluno_item",
            columnNames = {"aluno_id", "item_id"}
        )
    }
)
@Getter
@Setter
public class CompraItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemLoja item;

    @Column(nullable = false)
    private LocalDateTime dataCompra;

    @PrePersist
    protected void onCreate() {
        this.dataCompra = LocalDateTime.now();
    }
}
