package com.proyecto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.demo.entities.rol;

public interface rolRepository extends JpaRepository<rol, Long> {
    rol findByNombre(String nombre);
}