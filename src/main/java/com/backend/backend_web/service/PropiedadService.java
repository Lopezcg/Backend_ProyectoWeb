package com.backend.backend_web.service;

import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.entity.Propiedad;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.PropiedadRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public PropiedadDTO get(Long id) throws RegistroNoEncontradoException {
        Optional<Propiedad> propiedadOptional = repository.findById(id);
        if (!propiedadOptional.isPresent()) {
            throw new RegistroNoEncontradoException("Propiedad no encontrada con el ID: " + id);
        }
        return modelMapper.map(propiedadOptional.get(), PropiedadDTO.class);
    }

    public List<PropiedadDTO> get() {
        List<Propiedad> propiedades = (List<Propiedad>) repository.findAll();
        List<PropiedadDTO> propiedadesDTO = propiedades.stream()
                .map(propiedad -> modelMapper.map(propiedad, PropiedadDTO.class)).collect(Collectors.toList());
        return propiedadesDTO;
    }

    public PropiedadDTO save(PropiedadDTO propiedadDTO) throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (propiedadDTO == null) {
            throw new IllegalArgumentException("El DTO de Propiedad no puede ser nulo");
        }

        if (propiedadDTO.getNombre() == null || propiedadDTO.getValor() == null) {
            throw new IllegalArgumentException("El nombre y el valor de la propiedad son obligatorios");
        }

        Propiedad propiedad;

        try {
            propiedad = modelMapper.map(propiedadDTO, Propiedad.class);
            propiedad.setStatus(0); // Replace Status.ACTIVE with the appropriate value
            propiedad = repository.save(propiedad);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error al guardar la propiedad debido a una violación de integridad", e);
        } catch (Exception e) {
            // Capturar cualquier otra excepción no especificada
            throw new IllegalStateException("Error inesperado al guardar la propiedad", e);
        }

        propiedadDTO.setId(propiedad.getId());
        return propiedadDTO;
    }

    public PropiedadDTO update(PropiedadDTO propiedadDTO) throws RegistroNoEncontradoException {
        if (propiedadDTO == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        Propiedad propiedad = modelMapper.map(get(propiedadDTO.getId()), Propiedad.class);
        propiedad.setNombre(propiedadDTO.getNombre());
        propiedad.setDescripcion(propiedadDTO.getDescripcion());
        propiedad.setValor(propiedadDTO.getValor());
        propiedad.setEstado(propiedadDTO.getEstado());

        propiedad = repository.save(propiedad);
        propiedadDTO = modelMapper.map(propiedad, PropiedadDTO.class);
        return propiedadDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
