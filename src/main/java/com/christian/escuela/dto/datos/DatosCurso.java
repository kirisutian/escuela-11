package com.christian.escuela.dto.datos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un curso")
public record DatosCurso(

        @Schema(description = "Nombre del curso", example = "Matemáticas I")
        String nombre,

        @Schema(description = "Descripción del curso", example = "Curso de cálculo integral")
        String descripcion,

        @Schema(description = "Créditos del curso", example = "5")
        Integer creditos
) {}
