package com.backend.backend_web.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.repository.ArrendatarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class ArrendatarioService {
    @Autowired
    ArrendatarioRepository repository;
    @Autowired
    ModelMapper modelMapper;
    public ArrendadorDTO autorizacion(Authentication authentication) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("-----------------------");
        System.out.println(  authentication.getName() );
        ArrendadorDTO usuario = objectMapper.readValue(authentication.getName(), ArrendadorDTO.class);
        System.out.println("-----------------------"); 
        System.out.println(usuario+"USUARIO");
        return usuario;
    }

    public ArrendadorDTO get(Long id) throws RegistroNoEncontradoException {
        Optional<Arrendatario> arrendatarioOptional = repository.findById(id);
        if (!arrendatarioOptional.isPresent()) {
            throw new RegistroNoEncontradoException("Arrendatario no encontrado con el ID: " + id);
        }
        return modelMapper.map(arrendatarioOptional.get(), ArrendadorDTO.class);
    }

    public List<ArrendadorDTO> get() {
        List<Arrendatario> arrendatarios = (List<Arrendatario>) repository.findAll();
        List<ArrendadorDTO> arrendatariosDTO = arrendatarios.stream()
                .map(arrendatario -> modelMapper.map(arrendatario, ArrendadorDTO.class)).collect(Collectors.toList());
        return arrendatariosDTO;
    }

    public Arrendatario save(Arrendatario arrendatarioDTO) throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (arrendatarioDTO == null) {
            throw new IllegalArgumentException("El DTO de Arrendatario no puede ser nulo");
        }

        if (arrendatarioDTO.getNombre() == null || arrendatarioDTO.getCorreo() == null
                || arrendatarioDTO.getApellido() == null
                || arrendatarioDTO.getTelefono() == null || arrendatarioDTO.getContrasena() == null) {
            throw new IllegalArgumentException("Faltan campos requeridos en el DTO de Arrendatario");
        }

        Arrendatario arrendatario;

        try {
            arrendatario = modelMapper.map(arrendatarioDTO, Arrendatario.class);
            arrendatario.setStatus(0); // Replace Status.ACTIVE with the appropriate value
            arrendatario = repository.save(arrendatario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error al guardar el arrendatario debido a una violación de integridad", e);
        } catch (Exception e) {
            throw new IllegalStateException("Error inesperado al guardar el arrendatario", e);
        }

        arrendatarioDTO.setId(arrendatario.getId());
        return arrendatarioDTO;
    }

    public ArrendadorDTO update(Arrendatario arrendatarioDTO)
            throws ValidationException, IllegalArgumentException, RegistroNoEncontradoException {
        if (arrendatarioDTO == null) {
            throw new IllegalArgumentException("El objeto ArrendatarioDTO proporcionado es nulo");
        }

        Arrendatario arrendatarioExistente = repository.findById(arrendatarioDTO.getId())
                .orElseThrow(() -> new RegistroNoEncontradoException(
                        "Registro no encontrado para el ID: " + arrendatarioDTO.getId()));

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

    public void delete(Long id) throws RegistroNoEncontradoException {
        Optional<Arrendatario> arrendatario = repository.findById(id);
        if (!arrendatario.isPresent()) {
            throw new RegistroNoEncontradoException(
                    "Arrendatario no encontrado con el ID: " + id + " por lo tanto no se puede eliminar");
        }
        repository.deleteById(id);
    }

    public Optional<ArrendadorDTO> login(String correo, String contrasena) {
        Optional<Arrendatario> arrendatario = repository.findByCorreoAndContrasena(correo, contrasena);
        return arrendatario.map(a -> modelMapper.map(a, ArrendadorDTO.class));
    }

}
