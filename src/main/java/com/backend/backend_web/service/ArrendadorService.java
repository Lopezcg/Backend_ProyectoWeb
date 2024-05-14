package com.backend.backend_web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.Propiedad;
import com.backend.backend_web.repository.ArrendadorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArrendadorService {

    @Autowired
    ArrendadorRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public ArrendadorDTO get(Long id) {
        Optional<Arrendador> arrendadorOptional = repository.findById(id);

        if (arrendadorOptional.isPresent()) {
            return modelMapper.map(arrendadorOptional.get(), ArrendadorDTO.class);
        } else {
            throw new EntityNotFoundException("Arrendador no encontrado con el ID: " + id);
        }
    }

    public List<ArrendadorDTO> get() {
        List<Arrendador> arrendadores = (List<Arrendador>) repository.findAll();
        List<ArrendadorDTO> arrendadoresDTO = arrendadores.stream()
                .map(arrendador -> modelMapper.map(arrendador, ArrendadorDTO.class)).collect(Collectors.toList());
        return arrendadoresDTO;
    }

    public Arrendador save(Arrendador arrendadorDTO) {
        Arrendador arrendador = modelMapper.map(repository.save(arrendadorDTO), Arrendador.class);
        arrendador.setStatus(0); // Replace Status.ACTIVE with the appropriate value
        arrendadorDTO.setId(arrendador.getId());
        return arrendadorDTO;
    }

    public ArrendadorDTO update(Arrendador arrendadorDTO) {
        System.out.println("ENTREEEEE");
        Arrendador arrendadorExistente = repository.findById(arrendadorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

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

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<ArrendadorDTO> login(String correo, String contrasena) {
        Optional<Arrendador> arrendador = repository.findByCorreoAndContrasena(correo, contrasena);
        return arrendador.map(a -> modelMapper.map(a, ArrendadorDTO.class));
    }

}
