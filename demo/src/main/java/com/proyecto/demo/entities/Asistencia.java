package com.proyecto.demo.entities;

import java.time.LocalDate;

import com.proyecto.demo.enums.TipoAsistencia;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "asistencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAsistencia tipo;

    @Column
    private Integer minutosTarde; 
}
