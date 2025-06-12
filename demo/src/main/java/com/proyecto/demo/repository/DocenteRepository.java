package com.proyecto.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.demo.entities.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByContraSecreta(String contraSecreta);

}