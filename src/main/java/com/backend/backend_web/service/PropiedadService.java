package com.backend.backend_web.service;

import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.entity.Propiedad;
import com.backend.backend_web.repository.PropiedadRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public PropiedadDTO get(Long id) {
        Optional<Propiedad> propiedadOptional = repository.findById(id);
        if (!propiedadOptional.isPresent()) {
            throw new EntityNotFoundException("Propiedad no encontrada con el ID: " + id);
        }
        return modelMapper.map(propiedadOptional.get(), PropiedadDTO.class);
    }

    public List<PropiedadDTO> get() {
        List<Propiedad> propiedades = (List<Propiedad>) repository.findAll();
        List<PropiedadDTO> propiedadesDTO = propiedades.stream()
                .map(propiedad -> modelMapper.map(propiedad, PropiedadDTO.class)).collect(Collectors.toList());
        return propiedadesDTO;
    }

    public PropiedadDTO save(PropiedadDTO propiedadDTO) {
        Propiedad propiedad = modelMapper.map(propiedadDTO, Propiedad.class);
        propiedad.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        propiedad = repository.save(propiedad);
        propiedadDTO.setId(propiedad.getId());
        return propiedadDTO;
    }

    public PropiedadDTO update(PropiedadDTO propiedadDTO) {
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
