package com.decoramais.decoramais_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decoramais.decoramais_backend.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}