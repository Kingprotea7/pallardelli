package com.proyecto.demo.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltroAsistenciaDTO {
    private LocalDate desde;
    private LocalDate hasta;
    private String docente; // nombre o parte del nombre
    private String condicionLaboral;
}