package com.backend.backend_web.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.repository.ArrendatarioRepository;

import ch.qos.logback.core.status.Status;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class ArrendatarioService {
    @Autowired
    ArrendatarioRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public ArrendatarioDTO get(UUID id) {
        Optional<Arrendatario> arrendatarioOptional = repository.findById(id);
        ArrendatarioDTO arrendatarioDTO = null;
        if (arrendatarioOptional != null) {
            arrendatarioDTO = modelMapper.map(arrendatarioOptional.get(), ArrendatarioDTO.class);
        }
        return arrendatarioDTO;
    }

    public List<ArrendatarioDTO> get() {
        List<Arrendatario> arrendatarios = (List<Arrendatario>) repository.findAll();
        List<ArrendatarioDTO> arrendatariosDTO = arrendatarios.stream()
                .map(arrendatario -> modelMapper.map(arrendatario, ArrendatarioDTO.class)).collect(Collectors.toList());
        return arrendatariosDTO;
    }

    public ArrendatarioDTO save(ArrendatarioDTO arrendatarioDTO) {
        Arrendatario arrendatario = modelMapper.map(arrendatarioDTO, Arrendatario.class);
        arrendatario.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        arrendatario = repository.save(arrendatario);
        arrendatarioDTO.setId(arrendatario.getId());
        return arrendatarioDTO;
    }

    public ArrendatarioDTO update(ArrendatarioDTO arrendatarioDTO) throws ValidationException {
        arrendatarioDTO = get(arrendatarioDTO.getId());
        if (arrendatarioDTO == null) {
            throw new ValidationException(null);
        }
        Arrendatario arrendatario = modelMapper.map(arrendatarioDTO, Arrendatario.class);
        arrendatario.setStatus(0);
        arrendatario = repository.save(arrendatario);
        arrendatarioDTO = modelMapper.map(arrendatario, ArrendatarioDTO.class);
        return arrendatarioDTO;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
