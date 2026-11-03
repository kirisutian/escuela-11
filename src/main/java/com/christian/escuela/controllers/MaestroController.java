package com.christian.escuela.controllers;

import com.christian.escuela.dto.maestros.MaestroRequest;
import com.christian.escuela.dto.maestros.MaestroResponse;
import com.christian.escuela.services.maestros.MaestroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maestros")
@Tag(name = "API Maestros", description = "Métodos para gestión de maestros")
public class MaestroController extends CrudController<MaestroRequest, MaestroResponse, MaestroService>{

    public MaestroController(MaestroService service) {
        super(service);
    }
}