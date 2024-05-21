package com.backend.backend_web.service;

import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.entity.Arrendador;
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

    public List<PropiedadDTO> getPropiedadByArrendador(Long id) {
        List<Propiedad> propiedades = repository.findByArrendadorId(id);
        List<PropiedadDTO> propiedadesDTO = propiedades.stream()
                .map(propiedad -> modelMapper.map(propiedad, PropiedadDTO.class)).collect(Collectors.toList());
        return propiedadesDTO;
    }
    public ArrendadorDTO getArrendadorDTO(Long id) {
        Propiedad propiedad= repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Arrendador arrendador= propiedad.getArrendador();
        return modelMapper.map(arrendador, ArrendadorDTO.class);
    }

    public List<PropiedadDTO> get() {
        List<Propiedad> propiedades = (List<Propiedad>) repository.findAll();
        List<PropiedadDTO> propiedadesDTO = propiedades.stream()
                .map(propiedad -> modelMapper.map(propiedad, PropiedadDTO.class)).collect(Collectors.toList());
        return propiedadesDTO;
    }

    public PropiedadDTO save(PropiedadDTO propiedadDTO, ArrendadorDTO arrendadorDTO)
            throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (propiedadDTO == null) {
            throw new IllegalArgumentException("El DTO de Propiedad no puede ser nulo");
        }

        if (propiedadDTO.getNombre() == null || propiedadDTO.getValor() == null) {
            throw new IllegalArgumentException("El nombre y el valor de la propiedad son obligatorios");
        }

        Propiedad propiedad;
        Arrendador arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);
        try {
            propiedad = modelMapper.map(propiedadDTO, Propiedad.class);
            propiedad.setStatus(0); // Replace Status.ACTIVE with the appropriate value
            propiedad.setArrendador(arrendador);
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

    public PropiedadDTO update(PropiedadDTO propiedadDTO, ArrendadorDTO arrendadorDTO)
            throws RegistroNoEncontradoException, IllegalArgumentException {
        if (propiedadDTO == null) {
            throw new IllegalArgumentException("El objeto PropiedadDTO proporcionado es nulo");
        }

        // Recuperar la entidad Propiedad existente desde la base de datos
        Propiedad propiedad = repository.findById(propiedadDTO.getId())
                .orElseThrow(() -> new RegistroNoEncontradoException(
                        "Registro no encontrado para el ID: " + propiedadDTO.getId()));
        if (!propiedad.getArrendador().getId().equals(arrendadorDTO.getId())) {
            throw new IllegalArgumentException("La propiedad no pertenece al arrendador proporcionado");
        }
        // Actualizar los campos con los valores proporcionados desde el DTO
        propiedad.setNombre(propiedadDTO.getNombre());
        propiedad.setDescripcion(propiedadDTO.getDescripcion());
        propiedad.setValor(propiedadDTO.getValor());
        propiedad.setEstado(propiedadDTO.getEstado());
        propiedad.setAsador(propiedadDTO.getAsador());
        propiedad.setBanos(propiedadDTO.getBanos());
        propiedad.setHabitaciones(propiedadDTO.getHabitaciones());
        propiedad.setPiscina(propiedadDTO.getPiscina());
        propiedad.setMascotas(propiedadDTO.getMascotas());

        // Guardar la entidad Propiedad actualizada en la base de datos
        propiedad = repository.save(propiedad);

        // Mapear la entidad actualizada de vuelta a DTO para devolverla
        PropiedadDTO updatedPropiedadDTO = modelMapper.map(propiedad, PropiedadDTO.class);
        return updatedPropiedadDTO;
    }

    public void delete(Long id, ArrendadorDTO arrendadorDTO) throws RegistroNoEncontradoException {
        Propiedad propiedad = repository.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException(
                        "Registro no encontrado para el ID: " + id));
        if (!propiedad.getArrendador().getId().equals(arrendadorDTO.getId())) {
            throw new IllegalArgumentException("La propiedad no pertenece al arrendador proporcionado");
        }
        repository.deleteById(id);
    }

}
