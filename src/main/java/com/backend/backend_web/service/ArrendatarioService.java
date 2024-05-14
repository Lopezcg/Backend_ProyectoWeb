package com.backend.backend_web.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.backend.backend_web.entity.Arrendador;
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

    public ArrendadorDTO update(Arrendatario arrendatarioDTO) throws ValidationException {
        System.out.println("ENTREEEEE");
        Arrendatario arrendatarioExistente = repository.findById(arrendatarioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        // Actualizar los campos escalares
        arrendatarioExistente.setApellido(arrendatarioDTO.getApellido());
        arrendatarioExistente.setNombre(arrendatarioDTO.getNombre());
        arrendatarioExistente.setCorreo(arrendatarioDTO.getCorreo());
        arrendatarioExistente.setTelefono(arrendatarioDTO.getTelefono());
        arrendatarioExistente.setContrasena(arrendatarioDTO.getContrasena());

        // No modificar la colección de propiedades aquí

        // Guardar los cambios en la base de datos
        arrendatarioExistente = repository.save(arrendatarioExistente);

        // Convertir de nuevo a DTO para devolver
        ArrendadorDTO testDTO = modelMapper.map(arrendatarioExistente, ArrendadorDTO.class);
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
