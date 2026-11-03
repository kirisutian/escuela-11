package com.christian.escuela.services.maestros;

import com.christian.escuela.dto.maestros.MaestroRequest;
import com.christian.escuela.dto.maestros.MaestroResponse;
import com.christian.escuela.entities.Maestro;
import com.christian.escuela.exceptions.ConflictoException;
import com.christian.escuela.exceptions.EntidadRelacionadaException;
import com.christian.escuela.mappers.MaestroMapper;
import com.christian.escuela.repositories.GrupoRepository;
import com.christian.escuela.repositories.MaestroRepository;
import com.christian.escuela.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MaestroServiceImpl implements MaestroService {

    private final MaestroRepository maestroRepository;

    private final GrupoRepository grupoRepository;

    private final MaestroMapper maestroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {

        log.info("Listando todos los maestros");

        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo maestro...");

        validarDatosUnicos(request);

        Maestro maestro = maestroMapper.requestAEntidad(request);

        maestroRepository.saveAndFlush(maestro);

        log.info("Nuevo maestro {} registrado", maestro.obtenerNombreCompleto());

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Actualizando maestro con id: {}", id);

        validarCambiosUnicos(request, id);

        maestro.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono());

        maestroRepository.saveAndFlush(maestro);

        log.info("Maestro {} actualizado", maestro.obtenerNombreCompleto());

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Eliminando maestro con id: {}", id);

        if (grupoRepository.existsByMaestroId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el maestro ya que tiene grupos asignados");

        maestroRepository.delete(maestro);
        maestroRepository.flush();

        log.info("Maestro {} eliminado", maestro.obtenerNombreCompleto());
    }

    private Maestro obtenerMaestro(Long id) {
        return ServiceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    public void validarDatosUnicos(MaestroRequest request) {

        log.info("Validando email único...");

        if (maestroRepository.existsByEmailIgnoreCase(request.email().trim()))
            throw new ConflictoException("Ya existe un maestro registrado con el email: " + request.email());

        log.info("Validando teléfono único...");

        if (maestroRepository.existsByTelefono(request.telefono().trim()))
            throw new ConflictoException("Ya existe un maestro registrado con el teléfono: " + request.telefono());
    }

    public void validarCambiosUnicos(MaestroRequest request, Long id) {

        log.info("Validando cambio en email único...");

        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email().trim(), id))
            throw new ConflictoException("Ya existe un maestro registrado con el email: " + request.email());

        log.info("Validando cambio en teléfono único...");

        if (maestroRepository.existsByTelefonoAndIdNot(request.telefono().trim(), id))
            throw new ConflictoException("Ya existe un maestro registrado con el teléfono: " + request.telefono());
    }
}
