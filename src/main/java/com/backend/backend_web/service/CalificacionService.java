package com.backend.backend_web.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.CalificacionRepository;

@Service
public class CalificacionService {
    @Autowired
    CalificacionRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public CalificacionService(CalificacionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public CalificacionDTO get(Long id) throws RegistroNoEncontradoException {
        Optional<Calificacion> calificacionOptional = repository.findById(id);
        if (!calificacionOptional.isPresent()) {
            throw new RegistroNoEncontradoException("Calificación no encontrada con el ID: " + id);
        }
        return modelMapper.map(calificacionOptional.get(), CalificacionDTO.class);
    }

    public List<CalificacionDTO> get() {
        List<Calificacion> Calificaciones = (List<Calificacion>) repository.findAll();
        List<CalificacionDTO> CalificacionesDTO = Calificaciones.stream()
                .map(Calificacion -> modelMapper.map(Calificacion, CalificacionDTO.class)).collect(Collectors.toList());
        return CalificacionesDTO;
    }

    public List<CalificacionDTO> getCalificacionByPropiedad(Long id) {
        List<Calificacion> Calificaciones = repository.findByPropiedadId(id);
        List<CalificacionDTO> CalificacionesDTO = Calificaciones.stream()
                .map(Calificacion -> modelMapper.map(Calificacion, CalificacionDTO.class)).collect(Collectors.toList());
        return CalificacionesDTO;
    }

    public CalificacionDTO save(CalificacionDTO CalificacionDTO)
            throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (CalificacionDTO == null) {
            throw new IllegalArgumentException("El DTO de Calificación no puede ser nulo");
        }

        if (CalificacionDTO.getComentario() == null || CalificacionDTO.getPuntuacion() == null) {
            throw new IllegalArgumentException("Faltan campos requeridos en el DTO de Calificación");
        }

        Calificacion Calificacion = modelMapper.map(CalificacionDTO, Calificacion.class);

        Calificacion.setStatus(0);
        try {
            Calificacion = repository.save(Calificacion);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error al guardar la calificación debido a una violación de integridad", e);
        }

        CalificacionDTO.setId(Calificacion.getId());
        return CalificacionDTO;
    }

    public CalificacionDTO update(CalificacionDTO CalificacionDTO)
            throws RegistroNoEncontradoException, IllegalArgumentException {
        if (CalificacionDTO == null) {
            throw new IllegalArgumentException("El objeto CalificacionDTO proporcionado es nulo");

        }

        // Intentar recuperar la entidad Calificacion existente desde la base de datos
        final CalificacionDTO finalCalificacionDTO = CalificacionDTO;
        Calificacion Calificacion = repository.findById(finalCalificacionDTO.getId())
                .orElseThrow(() -> new RegistroNoEncontradoException(
                        "Registro no encontrado para el ID: " + finalCalificacionDTO.getId()));

        // Actualizar los campos de Calificacion
        Calificacion.setStatus(0); // Suponiendo que 0 representa un estado particular
        Calificacion.setComentario(finalCalificacionDTO.getComentario());
        Calificacion.setPuntuacion(finalCalificacionDTO.getPuntuacion());

        Calificacion = repository.save(Calificacion);
        CalificacionDTO = modelMapper.map(Calificacion, CalificacionDTO.class);
        return CalificacionDTO;
    }

    public void delete(Long id) throws RegistroNoEncontradoException {
        Optional<Calificacion> calificacion = repository.findById(id);
        if (!calificacion.isPresent()) {
            throw new RegistroNoEncontradoException(
                    "Calificacion no encontrada con ID " + id + " por lo tanto no se puede eliminar");
        }
        repository.deleteById(id);
    }

}
