package com.decoramais.decoramais_backend.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.decoramais.decoramais_backend.entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    
}
