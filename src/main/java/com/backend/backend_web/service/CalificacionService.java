package com.backend.backend_web.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.repository.CalificacionRepository;

@Service
class CalificacionService {
    @Autowired
    CalificacionRepository repository;
    @Autowired
    ModelMapper modelMapper;

    CalificacionService(CalificacionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    CalificacionDTO get(Long id) {
        Optional<Calificacion> calificacionOptional = repository.findById(id);
        if (!calificacionOptional.isPresent()) {
            throw new NoSuchElementException("No se encontró una Calificación con el ID: " + id);
        }
        return modelMapper.map(calificacionOptional.get(), CalificacionDTO.class);
    }

    List<CalificacionDTO> get() {
        List<Calificacion> Calificaciones = (List<Calificacion>) repository.findAll();
        List<CalificacionDTO> CalificacionesDTO = Calificaciones.stream()
                .map(Calificacion -> modelMapper.map(Calificacion, CalificacionDTO.class)).collect(Collectors.toList());
        return CalificacionesDTO;
    }

    CalificacionDTO save(CalificacionDTO CalificacionDTO) {
        Calificacion Calificacion = modelMapper.map(CalificacionDTO, Calificacion.class);
        Calificacion.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        Calificacion = repository.save(Calificacion);
        CalificacionDTO.setId(Calificacion.getId());
        return CalificacionDTO;
    }

    CalificacionDTO update(CalificacionDTO CalificacionDTO) {
        CalificacionDTO = get(CalificacionDTO.getId());
        if (CalificacionDTO == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        Calificacion Calificacion = modelMapper.map(CalificacionDTO, Calificacion.class);
        Calificacion.setStatus(0);
        Calificacion = repository.save(Calificacion);
        CalificacionDTO = modelMapper.map(Calificacion, CalificacionDTO.class);
        return CalificacionDTO;
    }

    void delete(Long id) {
        repository.deleteById(id);
    }

}
