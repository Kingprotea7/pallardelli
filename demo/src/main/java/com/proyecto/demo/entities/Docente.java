package com.proyecto.demo.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "docentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Docente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String dni;
    @Column(nullable = false)
    private String nombre;
    @Column
    private String nombre2;
    @Column(nullable = false)
    private String apellido1;
    @Column
    private String apellido2;
    @Column
    private String telefono;
    @Column(name = "contra_secreta", nullable = false)
    private String contraSecreta;

    @Column(nullable = false)
    private String cargo;
  @Column(name = "condicion_laboral")
private String condicionLaboral;

    @Column(nullable = false)
    private Integer horasJornada;
  
    private boolean esTutor;



}
