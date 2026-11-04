package com.christian.escuela.mappers;

import com.christian.escuela.dto.alumnos.AlumnoRequest;
import com.christian.escuela.dto.alumnos.AlumnoResponse;
import com.christian.escuela.dto.datos.DatosCalificacion;
import com.christian.escuela.entities.Alumno;
import com.christian.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {

        return request == null
                ? null
                : Alumno.crear(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno());
    }

    public Alumno requestAEntidad(AlumnoRequest request, String email, String matricula) {

        if(request == null) return null;

        Alumno alumno = requestAEntidad(request);
        alumno.asignarDatosAcademicos(email, matricula);
        return alumno;
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {

        return entidad == null
                ? null
                : new AlumnoResponse(
                    entidad.getId(),
                    entidad.obtenerNombreCompleto(),
                    entidad.getEmail(),
                    entidad.getMatricula(),
                    StringCustomUtils.localDateAString(
                            entidad.getFechaIngreso()),
                    entidadADatosCalificacion(entidad),
                    entidad.calcularPromedio());
    }

    private List<DatosCalificacion> entidadADatosCalificacion(Alumno entidad) {

        if (entidad == null || entidad.getInscripciones() == null || entidad.getInscripciones().isEmpty())
            return List.of();

        return entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null
                                ? inscripcion.getCalificacion().getCalificacion()
                                : null
                )).toList();
    }
}
