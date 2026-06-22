package com.decoramais.decoramais_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.decoramais.decoramais_backend.entity.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    Optional<Sala> findByCodigConvite(String codigConvite);
}