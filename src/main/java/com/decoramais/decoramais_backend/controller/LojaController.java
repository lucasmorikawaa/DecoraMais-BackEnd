package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.loja.CompraItemResponseDTO;
import com.decoramais.decoramais_backend.dto.loja.ItemLojaResponseDTO;
import com.decoramais.decoramais_backend.entity.Usuario;
import com.decoramais.decoramais_backend.service.LojaService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loja")
public class LojaController {

    private final LojaService lojaService;

    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    // Lista os itens disponíveis na loja
    @GetMapping
    public ResponseEntity<List<ItemLojaResponseDTO>> listarItens() {

        return ResponseEntity.ok(
                lojaService.listarItensDisponiveis()
        );
    }

    // Lista os itens comprados pelo aluno autenticado
    @GetMapping("/meus-itens")
    public ResponseEntity<List<CompraItemResponseDTO>> listarMeusItens(
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(
                lojaService.listarMeusItens(usuario.getId())
        );
    }

    // Compra um item utilizando XP
    @PostMapping("/comprar/{itemId}")
    public ResponseEntity<CompraItemResponseDTO> comprarItem(
            @PathVariable Long itemId,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(
                lojaService.comprarItem(usuario.getId(), itemId)
        );
    }
}