package com.example.test.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaRequest {

    @NotNull(message="No puede ser null")
    private Long idCurso;

    @NotNull(message="No puede ser null")
    private Long idUsuario;

    @NotNull(message="No puede ser null")
    private Integer ciclo;

    @NotNull(message="No puede ser null")
    private Double nota1;

    @NotNull(message="No puede ser null")
    private Double nota2;

    @NotNull(message="No puede ser null")
    private Double nota3;

    @NotNull(message="No puede ser null")
    private Double nota4;

}
