package com.decoramais.decoramais_backend.repository;

import com.decoramais.decoramais_backend.entity.ItemLoja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemLojaRepository extends JpaRepository<ItemLoja, Long> {

    List<ItemLoja> findByAtivoTrue();

}