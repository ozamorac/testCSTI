package com.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="notacurso")
public class Nota implements Serializable  {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="IdCurso")
    private Long idCurso;

    @Column(name="IdUsuario")
    private Long idUsuario;

    @Column(name="Ciclo")
    private Integer ciclo;

    @Column(name="Nota1")
    private Double nota1;

    @Column(name="Nota2")
    private Double nota2;

    @Column(name="Nota3")
    private Double nota3;

    @Column(name="Nota4")
    private Double nota4;

}
