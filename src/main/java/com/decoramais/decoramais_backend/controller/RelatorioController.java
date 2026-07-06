package com.decoramais.decoramais_backend.controller;

import com.decoramais.decoramais_backend.dto.RelatorioTurmaDTO;
import com.decoramais.decoramais_backend.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<RelatorioTurmaDTO> obterRelatorioTurma(@PathVariable Long salaId) {
        RelatorioTurmaDTO relatorio = relatorioService.gerarRelatorioDaTurma(salaId);
        return ResponseEntity.ok(relatorio);
    }
}