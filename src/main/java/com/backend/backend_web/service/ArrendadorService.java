package com.backend.backend_web.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.repository.ArrendadorRepository;

@Service
public class ArrendadorService {

    @Autowired
    ArrendadorRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public ArrendadorDTO get(UUID id) {
        Optional<Arrendador> arrendadorOptional = repository.findById(id);
        ArrendadorDTO arrendadorDTO = null;
        if (arrendadorOptional != null) {
            arrendadorDTO = modelMapper.map(arrendadorOptional.get(), ArrendadorDTO.class);
        }
        return arrendadorDTO;
    }

    public Iterable<Arrendador> get() {
        return repository.findAll();
    }

    public ArrendadorDTO save(ArrendadorDTO arrendadorDTO) {
        Arrendador arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);
        arrendador.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        arrendador = repository.save(arrendador);
        arrendadorDTO.setId(arrendador.getId());
        return arrendadorDTO;
    }

    public ArrendadorDTO update(ArrendadorDTO arrendadorDTO) {
        arrendadorDTO = get(arrendadorDTO.getId());
        if (arrendadorDTO == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        Arrendador arrendador = modelMapper.map(arrendadorDTO, Arrendador.class);
        arrendador.setStatus(0);
        arrendador = repository.save(arrendador);
        arrendadorDTO = modelMapper.map(arrendador, ArrendadorDTO.class);
        return arrendadorDTO;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
