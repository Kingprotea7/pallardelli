package com.proyecto.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.demo.entities.Docente;
import com.proyecto.demo.repository.DocenteRepository;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public Docente crearDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    public List<Docente> obtenerTodos() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> obtenerPorId(Long id) {
        return docenteRepository.findById(id);
    }

    public Optional<Docente> buscarPorContraSecreta(String contraSecreta) {
        return docenteRepository.findByContraSecreta(contraSecreta);
    }
    
}