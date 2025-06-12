package com.proyecto.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.entities.Asistencia;
import com.proyecto.demo.entities.Docente;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    boolean existsByDocenteAndFecha(Docente docente, LocalDate fecha);

    List<Asistencia> findAllByOrderByFechaDesc();
    
}

