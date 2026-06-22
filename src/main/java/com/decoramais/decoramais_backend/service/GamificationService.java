package com.decoramais.decoramais_backend.service;

import com.decoramais.decoramais_backend.entity.Aluno;
import com.decoramais.decoramais_backend.entity.HistoricoEstudo;
import com.decoramais.decoramais_backend.repository.AlunoRepository;
import com.decoramais.decoramais_backend.repository.HistoricoEstudoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class GamificationService {

    private final AlunoRepository alunoRepository;
    private final HistoricoEstudoRepository historicoRepository;

    private static final int META_DIARIA_CARDS = 5; 
    private static final int XP_POR_CARD = 10;

    public GamificationService(AlunoRepository alunoRepository, HistoricoEstudoRepository historicoRepository) {
        this.alunoRepository = alunoRepository;
        this.historicoRepository = historicoRepository;
    }

    @Transactional
    public void processarGamificacao(Aluno aluno) {
        LocalDate hoje = LocalDate.now();
        LocalDate ontem = hoje.minusDays(1);

        aluno.setXp(aluno.getXp() + XP_POR_CARD);

        HistoricoEstudo historicoHoje = historicoRepository.findByAlunoIdAndData(aluno.getId(), hoje)
                .orElseGet(() -> {
                    HistoricoEstudo novo = new HistoricoEstudo();
                    novo.setAluno(aluno);
                    novo.setData(hoje);
                    return novo;
                });

        historicoHoje.setQuantidadeRevisada(historicoHoje.getQuantidadeRevisada() + 1);
        historicoRepository.save(historicoHoje);

        if (aluno.getDataUltimoEstudo() == null) {
            aluno.setOfensiva(1);
            aluno.setDataUltimoEstudo(hoje);
        } else if (!aluno.getDataUltimoEstudo().equals(hoje)) {
            
            if (aluno.getDataUltimoEstudo().equals(ontem)) {
                if (historicoHoje.getQuantidadeRevisada() == META_DIARIA_CARDS) {
                    aluno.setOfensiva(aluno.getOfensiva() + 1);
                    aluno.setDataUltimoEstudo(hoje);
                }
            } else {
                aluno.setOfensiva(1);
                aluno.setDataUltimoEstudo(hoje);
            }
        }
        
        alunoRepository.save(aluno);
    }
}