package com.backend.backend_web.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.repository.ArrendatarioRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class ArrendatarioService {
    @Autowired
    ArrendatarioRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public ArrendadorDTO get(Long id) {
        Optional<Arrendatario> arrendatarioOptional = repository.findById(id);
        if (!arrendatarioOptional.isPresent()) {
            throw new EntityNotFoundException("Arrendatario no encontrado con el ID: " + id);
        }
        return modelMapper.map(arrendatarioOptional.get(), ArrendadorDTO.class);
    }

    public List<ArrendadorDTO> get() {
        List<Arrendatario> arrendatarios = (List<Arrendatario>) repository.findAll();
        List<ArrendadorDTO> arrendatariosDTO = arrendatarios.stream()
                .map(arrendatario -> modelMapper.map(arrendatario, ArrendadorDTO.class)).collect(Collectors.toList());
        return arrendatariosDTO;
    }

    public Arrendatario save(Arrendatario ArrendadorDTO) {
        Arrendatario arrendatario = modelMapper.map(ArrendadorDTO, Arrendatario.class);
        arrendatario.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        arrendatario = repository.save(arrendatario);
        ArrendadorDTO.setId(arrendatario.getId());
        return ArrendadorDTO;
    }

    public ArrendadorDTO update(Arrendatario ArrendadorDTO) throws ValidationException {
        Arrendatario test = modelMapper.map(get(ArrendadorDTO.getId()), Arrendatario.class);
        if (test == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        test.setStatus(0);
        test.setApellido(ArrendadorDTO.getApellido());
        test.setNombre(ArrendadorDTO.getNombre());
        test.setCorreo(ArrendadorDTO.getCorreo());
        test.setTelefono(ArrendadorDTO.getTelefono());
        test.setContrasena(ArrendadorDTO.getContrasena());
        test = repository.save(test);
        ArrendadorDTO testDTO = modelMapper.map(test, ArrendadorDTO.class);
        return testDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<ArrendadorDTO> login(String correo, String contrasena) {
        Optional<Arrendatario> arrendatario = repository.findByCorreoAndContrasena(correo, contrasena);
        return arrendatario.map(a -> modelMapper.map(a, ArrendadorDTO.class));
    }

}
