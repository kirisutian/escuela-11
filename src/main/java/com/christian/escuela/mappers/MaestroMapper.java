package com.christian.escuela.mappers;

import com.christian.escuela.dto.datos.DatosCurso;
import com.christian.escuela.dto.maestros.MaestroRequest;
import com.christian.escuela.dto.maestros.MaestroResponse;
import com.christian.escuela.entities.Grupo;
import com.christian.escuela.entities.Maestro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {

    private final CursoMapper cursoMapper;

    @Override
    public Maestro requestAEntidad(MaestroRequest request) {

        return request == null
                ? null
                : Maestro.crear(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    request.email(),
                    request.telefono());
    }

    @Override
    public MaestroResponse entidadAResponse(Maestro entidad) {

        return entidad == null
                ? null
                : new MaestroResponse(
                    entidad.getId(),
                    entidad.obtenerNombreCompleto(),
                    entidad.getEmail(),
                    entidad.getTelefono(),
                    entidadADatosCurso(entidad));
    }

    public List<DatosCurso> entidadADatosCurso(Maestro entidad) {

        return entidad == null
                ? List.of()
                : entidad.getGrupos().stream()
                    .map(Grupo::getCurso)
                    .map(cursoMapper::entidadADatosCurso)
                    .toList();
    }
}
