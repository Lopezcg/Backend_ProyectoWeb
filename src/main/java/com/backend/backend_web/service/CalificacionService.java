package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.repository.CalificacionRepository;

@Service
public class CalificacionService {
    @Autowired
    CalificacionRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public CalificacionDTO get(UUID id) {
        Optional<Calificacion> CalificacionOptional = repository.findById(id);
        CalificacionDTO CalificacionDTO = null;
        if (CalificacionOptional != null) {
            CalificacionDTO = modelMapper.map(CalificacionOptional.get(), CalificacionDTO.class);
        }
        return CalificacionDTO;
    }

    public List<CalificacionDTO> get() {
        List<Calificacion> Calificaciones = (List<Calificacion>) repository.findAll();
        List<CalificacionDTO> CalificacionesDTO = Calificaciones.stream()
                .map(Calificacion -> modelMapper.map(Calificacion, CalificacionDTO.class)).collect(Collectors.toList());
        return CalificacionesDTO;
    }

    public CalificacionDTO save(CalificacionDTO CalificacionDTO) {
        Calificacion Calificacion = modelMapper.map(CalificacionDTO, Calificacion.class);
        Calificacion.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        Calificacion = repository.save(Calificacion);
        CalificacionDTO.setId(Calificacion.getId());
        return CalificacionDTO;
    }

    public CalificacionDTO update(CalificacionDTO CalificacionDTO) {
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

    public void delete(UUID id) {
        repository.deleteById(id);
    }


}
