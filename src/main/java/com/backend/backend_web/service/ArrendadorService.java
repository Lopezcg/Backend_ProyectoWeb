package com.backend.backend_web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.ArrendadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ArrendadorService {

    @Autowired
    ArrendadorRepository repository;
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
        Optional<Arrendador> arrendadorOptional = repository.findById(id);
        if (arrendadorOptional.isPresent()) {
            return modelMapper.map(arrendadorOptional.get(), ArrendadorDTO.class);
        } else {
            throw new RegistroNoEncontradoException("Arrendador no encontrado con el ID: " + id);
        }
    }

    public List<ArrendadorDTO> get() {
        List<Arrendador> arrendadores = (List<Arrendador>) repository.findAll();
        List<ArrendadorDTO> arrendadoresDTO = arrendadores.stream()
                .map(arrendador -> modelMapper.map(arrendador, ArrendadorDTO.class)).collect(Collectors.toList());
        return arrendadoresDTO;
    }

    public Arrendador save(Arrendador arrendadorDTO) throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        if (arrendadorDTO == null) {
            throw new IllegalArgumentException("El DTO de Arrendador no puede ser nulo");
        }

        if (arrendadorDTO.getNombre() == null || arrendadorDTO.getCorreo() == null
                || arrendadorDTO.getApellido() == null
                || arrendadorDTO.getTelefono() == null || arrendadorDTO.getContrasena() == null) {
            throw new IllegalArgumentException("Faltan campos requeridos en el DTO de Arrendador");
        }

        Arrendador arrendador;

        try {
            arrendador = modelMapper.map(repository.save(arrendadorDTO), Arrendador.class);
            arrendador.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error al guardar el arrendador debido a una violación de integridad", e);
        } catch (Exception e) {
            // Capturar cualquier otra excepción que no sea de integridad de datos
            throw new IllegalStateException("Error inesperado al guardar el arrendador", e);
        }

        arrendadorDTO.setId(arrendador.getId());
        return arrendadorDTO;
    }

    public ArrendadorDTO update(Arrendador arrendadorDTO)
            throws IllegalArgumentException, RegistroNoEncontradoException {
        if (arrendadorDTO == null) {
            throw new IllegalArgumentException("El objeto ArrendadorDTO proporcionado es nulo");
        }

        // Recuperar la entidad Arrendador existente desde la base de datos
        Arrendador arrendadorExistente = repository.findById(arrendadorDTO.getId())
                .orElseThrow(() -> new RegistroNoEncontradoException(
                        "Registro no encontrado para el ID: " + arrendadorDTO.getId()));

        // Actualizar los campos escalares
        arrendadorExistente.setApellido(arrendadorDTO.getApellido());
        arrendadorExistente.setNombre(arrendadorDTO.getNombre());
        arrendadorExistente.setCorreo(arrendadorDTO.getCorreo());
        arrendadorExistente.setTelefono(arrendadorDTO.getTelefono());
        arrendadorExistente.setContrasena(arrendadorDTO.getContrasena());

        // No modificar la colección de propiedades aquí

        // Guardar los cambios en la base de datos
        arrendadorExistente = repository.save(arrendadorExistente);

        // Convertir de nuevo a DTO para devolver
        ArrendadorDTO testDTO = modelMapper.map(arrendadorExistente, ArrendadorDTO.class);
        return testDTO;
    }

    public void delete(Long id) throws RegistroNoEncontradoException {
        Optional<Arrendador> arrendador = repository.findById(id);
        if (!arrendador.isPresent()) {
            throw new RegistroNoEncontradoException(
                    "Arrendador no encontrado con ID " + id + " por lo tanto no se puede eliminar");
        }

        repository.deleteById(id);
    }

    public Optional<ArrendadorDTO> login(String correo, String contrasena) {
        Optional<Arrendador> arrendador = repository.findByCorreoAndContrasena(correo, contrasena);
        return arrendador.map(a -> modelMapper.map(a, ArrendadorDTO.class));
    }

}
