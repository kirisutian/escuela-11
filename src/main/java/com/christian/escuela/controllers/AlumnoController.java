package com.christian.escuela.controllers;

import com.christian.escuela.dto.alumnos.AlumnoRequest;
import com.christian.escuela.dto.alumnos.AlumnoResponse;
import com.christian.escuela.services.alumnos.AlumnoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
@Tag(name = "API Alumnos", description = "Métodos para gestión de alumnos")
public class AlumnoController extends CrudController<AlumnoRequest, AlumnoResponse, AlumnoService> {

    public AlumnoController(AlumnoService service) {
        super(service);
    }
}
