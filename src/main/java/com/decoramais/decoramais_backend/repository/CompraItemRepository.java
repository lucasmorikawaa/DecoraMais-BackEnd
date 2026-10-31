package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.entity.CompraItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraItemRepository extends JpaRepository<CompraItem, Long> {

    boolean existsByAlunoIdAndItemId(Long alunoId, Long itemId);

    List<CompraItem> findByAlunoId(Long alunoId);

}