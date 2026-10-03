package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.entity.HistoricoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HistoricoEstudoRepository extends JpaRepository<HistoricoEstudo, Long> {
    Optional<HistoricoEstudo> findByAlunoIdAndData(Long alunoId, LocalDate data);
}