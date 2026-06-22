package com.decoramais.decoramais_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decoramais.decoramais_backend.entity.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    
}
