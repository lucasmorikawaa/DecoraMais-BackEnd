package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.dto.loja.CompraItemResponseDTO;
import com.decoramais.decoramais_backend.dto.loja.ItemLojaResponseDTO;
import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.CompraItem;
import com.decoramais.decoramais_backend.entity.ItemLoja;
import com.decoramais.decoramais_backend.repository.AlunoRepository;
import com.decoramais.decoramais_backend.repository.CompraItemRepository;
import com.decoramais.decoramais_backend.repository.ItemLojaRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LojaService {

    private final ItemLojaRepository itemLojaRepository;
    private final CompraItemRepository compraItemRepository;
    private final AlunoRepository alunoRepository;

    public LojaService(
            ItemLojaRepository itemLojaRepository,
            CompraItemRepository compraItemRepository,
            AlunoRepository alunoRepository) {

        this.itemLojaRepository = itemLojaRepository;
        this.compraItemRepository = compraItemRepository;
        this.alunoRepository = alunoRepository;
    }

    // Lista os itens ativos da loja
    public List<ItemLojaResponseDTO> listarItensDisponiveis() {

        return itemLojaRepository.findByAtivoTrue()
                .stream()
                .map(item -> new ItemLojaResponseDTO(
                        item.getId(),
                        item.getNome(),
                        item.getDescricao(),
                        item.getImagem(),
                        item.getCustoXp()
                ))
                .toList();
    }

    // Lista os itens comprados pelo aluno
    public List<CompraItemResponseDTO> listarMeusItens(Long alunoId) {

        List<CompraItem> compras = compraItemRepository.findByAlunoId(alunoId);

        return compras.stream()
                .map(compra -> new CompraItemResponseDTO(
                        compra.getId(),
                        compra.getItem().getId(),
                        compra.getItem().getNome(),
                        compra.getItem().getImagem(),
                        compra.getItem().getCustoXp(),
                        compra.getDataCompra(),
                        null
                ))
                .toList();
    }

    // Realiza a compra de um item
    @Transactional
    public CompraItemResponseDTO comprarItem(Long alunoId, Long itemId) {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado"
                ));

        ItemLoja item = itemLojaRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Item não encontrado"
                ));

        if (!Boolean.TRUE.equals(item.getAtivo())) {
            throw new IllegalArgumentException(
                    "Este item não está disponível na loja"
            );
        }

        if (compraItemRepository.existsByAlunoIdAndItemId(alunoId, itemId)) {
            throw new IllegalArgumentException(
                    "Você já comprou este item"
            );
        }

        if (aluno.getXp() == null || aluno.getXp() < item.getCustoXp()) {
            throw new IllegalArgumentException(
                    "XP insuficiente para realizar esta compra"
            );
        }

        // Desconta o XP do aluno
        aluno.setXp(aluno.getXp() - item.getCustoXp());
        alunoRepository.save(aluno);

        // Registra a compra
        CompraItem compra = new CompraItem();
        compra.setAluno(aluno);
        compra.setItem(item);

        compra = compraItemRepository.save(compra);

        return new CompraItemResponseDTO(
                compra.getId(),
                item.getId(),
                item.getNome(),
                item.getImagem(),
                item.getCustoXp(),
                compra.getDataCompra(),
                aluno.getXp()
        );
    }
}