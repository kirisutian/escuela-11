package com.christian.escuela.utils;

import com.christian.escuela.exceptions.RecursoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public class ServiceUtils {

    public static <E, ID> E obtenerEntidadOException(
            JpaRepository<E, ID> repository,
            ID id,
            Class<E> clase
    ) {

        log.info("Buscando {} con id: {}", clase.getSimpleName(), id);

        return repository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(
                        clase.getSimpleName() +
                                " no encontrado con id: " + id));
    }
}
