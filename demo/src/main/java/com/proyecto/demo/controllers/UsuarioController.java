package com.proyecto.demo.controllers;

import com.proyecto.demo.DTO.LoginRequest;
import com.proyecto.demo.entities.User;
import com.proyecto.demo.entities.rol;
import com.proyecto.demo.repository.UserRepository;
import com.proyecto.demo.repository.rolRepository;
import com.proyecto.demo.services.userServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private userServices usuarioService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private rolRepository rolRepository;

 @Autowired
private PasswordEncoder passwordEncoder;

   

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

  
    @PostMapping
    public User guardarUsuario(@RequestBody Map<String, Object> payload) {
        String nombre = (String) payload.get("nombre");
        String apellido = (String) payload.get("apellido");
        String dni = (String) payload.get("dni");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        Integer rolId = (Integer) payload.get("rol_id");
        //////////////////////

        if (StringUtils.isAnyBlank(nombre, apellido, dni, email, password)) {
        ////////////////////
            logger.warn("Datos incompletos para crear usuario");

            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            logger.warn("Email inválido: {}", email);
            throw new IllegalArgumentException("El email ingresado no es válido.");
        }

        rol rol = rolRepository.findById(Long.valueOf(rolId))
                .orElseThrow(() -> {
                    logger.error("Rol no encontrado con ID: {}", rolId);
                    return new RuntimeException("Rol no encontrado");
                });

        User usuario = new User();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEmail(email);

        // 👉 Encriptar la contraseña
        String hashedPassword = passwordEncoder.encode(password);
        
        usuario.setPassword(hashedPassword);

        usuario.setRol(rol);

        User guardado = usuarioService.guardarUsuario(usuario);
        logger.info("Usuario creado exitosamente: {} {}", nombre, apellido);

        return guardado;
    }
    
    @GetMapping
    public List<User> obtenerUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User usuario = userRepository.findByEmail(loginRequest.getEmail());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        // Validamos la contraseña encriptada con BCrypt
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(usuario);
    }

}