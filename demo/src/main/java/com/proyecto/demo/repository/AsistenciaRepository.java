package com.proyecto.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.entities.Asistencia;
import com.proyecto.demo.entities.Docente;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    boolean existsByDocenteAndFecha(Docente docente, LocalDate fecha);

    List<Asistencia> findAllByOrderByFechaDesc();
    

    @Query("""
            SELECT a FROM Asistencia a
            WHERE (:desde IS NULL OR a.fecha >= :desde)
              AND (:hasta IS NULL OR a.fecha <= :hasta)
              AND (:docenteNombre IS NULL OR LOWER(CONCAT(a.docente.nombre, ' ', a.docente.apellido1, ' ', a.docente.apellido2)) LIKE LOWER(CONCAT('%', :docenteNombre, '%')))
              AND (:condicion IS NULL OR LOWER(a.docente.condicionLaboral) = LOWER(:condicion))
            ORDER BY a.fecha DESC
            """)
    List<Asistencia> findByFiltros(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta,
            @Param("docenteNombre") String docenteNombre,
            @Param("condicion") String condicion);        

}

