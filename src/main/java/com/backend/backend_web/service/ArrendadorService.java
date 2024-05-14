package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.ArrendadorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArrendadorService {

    @Autowired
    ArrendadorRepository repository;
    @Autowired
    ModelMapper modelMapper;

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

    public ArrendadorDTO update(Arrendador arrendadorDTO) throws RegistroNoEncontradoException {
        Arrendador test = modelMapper.map(get(arrendadorDTO.getId()), Arrendador.class);
        if (test == null) {
            throw new RuntimeException("Registro no encontrado");
        }
        test.setStatus(0);
        test.setApellido(arrendadorDTO.getApellido());
        test.setNombre(arrendadorDTO.getNombre());
        test.setCorreo(arrendadorDTO.getCorreo());
        test.setTelefono(arrendadorDTO.getTelefono());
        test.setContrasena(arrendadorDTO.getContrasena());
        System.out.println(test);
        test = repository.save(test);
        ArrendadorDTO testDTO = modelMapper.map(test, ArrendadorDTO.class);
        return testDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<ArrendadorDTO> login(String correo, String contrasena) {
        Optional<Arrendador> arrendador = repository.findByCorreoAndContrasena(correo, contrasena);
        return arrendador.map(a -> modelMapper.map(a, ArrendadorDTO.class));
    }

}
