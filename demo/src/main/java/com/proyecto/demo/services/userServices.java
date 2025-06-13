package com.proyecto.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.demo.entities.User;
import com.proyecto.demo.repository.UserRepository;
@Service
public class userServices {
    @Autowired
    private UserRepository usuarioRepository;
    public User guardarUsuario(User usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public List<User> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    
}
