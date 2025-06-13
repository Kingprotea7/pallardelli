package com.proyecto.demo.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.DTO.FiltroAsistenciaDTO;
import com.proyecto.demo.entities.Asistencia;
import com.proyecto.demo.entities.Docente;
import com.proyecto.demo.enums.TipoAsistencia;
import com.proyecto.demo.repository.AsistenciaRepository;
import com.proyecto.demo.repository.DocenteRepository;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAsistencia(@RequestBody Map<String, String> payload) {
        String clave = payload.get("claveSecreta");

        Optional<Docente> docenteOpt = docenteRepository.findByContraSecreta(clave);
        if (docenteOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Clave secreta incorrecta");
        }

        Docente docente = docenteOpt.get();
        LocalDate hoy = LocalDate.now();

        boolean yaRegistrado = asistenciaRepository.existsByDocenteAndFecha(docente, hoy);
        if (yaRegistrado) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Ya registraste tu asistencia hoy");
        }

        LocalTime horaEntrada = LocalTime.of(7, 0);
        LocalTime ahora = LocalTime.now();

        Asistencia asistencia = new Asistencia();
        asistencia.setDocente(docente);
        asistencia.setFecha(hoy);

        if (!ahora.isAfter(horaEntrada)) {
            asistencia.setTipo(TipoAsistencia.ASISTENCIA);
            asistencia.setMinutosTarde(0);
        } else {
            long minutosTarde = Duration.between(horaEntrada, ahora).toMinutes();
            asistencia.setTipo(TipoAsistencia.TARDANZA);
            asistencia.setMinutosTarde((int) minutosTarde);
        }

        asistenciaRepository.save(asistencia);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"mensaje\": \"Asistencia registrada correctamente\"}");

    }


     @GetMapping("/todas")
    public ResponseEntity<List<Asistencia>> obtenerTodasOrdenadas() {
        List<Asistencia> asistencias = asistenciaRepository.findAllByOrderByFechaDesc();
        return ResponseEntity.ok(asistencias);
    }
    

@PostMapping("/filtrar")
public ResponseEntity<List<Asistencia>> filtrarAsistencias(@RequestBody FiltroAsistenciaDTO filtro) {
    LocalDate desde = filtro.getDesde();
    LocalDate hasta = filtro.getHasta();
    String docenteNombre = filtro.getDocente();
    String nivel =    filtro.getCondicionLaboral();
    
    List<Asistencia> asistencias = asistenciaRepository
        .findByFiltros(desde, hasta, docenteNombre, nivel);

    return ResponseEntity.ok(asistencias);
}



}    