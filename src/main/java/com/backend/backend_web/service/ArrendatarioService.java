package com.backend.backend_web.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.repository.ArrendatarioRepository;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class ArrendatarioService {
    @Autowired
    ArrendatarioRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public ArrendatarioDTO get(Long id) {
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

    public Arrendatario save(Arrendatario arrendatarioDTO) {
        Arrendatario arrendatario = modelMapper.map(arrendatarioDTO, Arrendatario.class);
        arrendatario.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        arrendatario = repository.save(arrendatario);
        arrendatarioDTO.setId(arrendatario.getId());
        return arrendatarioDTO;
    }

    public ArrendatarioDTO update(Arrendatario arrendatarioDTO) throws ValidationException {
        Arrendatario test = modelMapper.map(get(arrendatarioDTO.getId()), Arrendatario.class);
        if (test == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        test.setStatus(0);
        test.setApellido(arrendatarioDTO.getApellido());
        test.setNombre(arrendatarioDTO.getNombre());
        test.setCorreo(arrendatarioDTO.getCorreo());
        test.setTelefono(arrendatarioDTO.getTelefono());
        test.setContrasena(arrendatarioDTO.getContrasena());
        test = repository.save(test);
        ArrendatarioDTO testDTO = modelMapper.map(test, ArrendatarioDTO.class);
        return testDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
