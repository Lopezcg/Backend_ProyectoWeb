package com.backend.backend_web.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

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

    public ArrendadorDTO get(Long id) {
        Optional<Arrendador> arrendadorOptional = repository.findById(id);
        ArrendadorDTO arrendadorDTO = null;
        if (arrendadorOptional != null) {
            arrendadorDTO = modelMapper.map(arrendadorOptional.get(), ArrendadorDTO.class);
        }
        return arrendadorDTO;
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
        test = repository.save(test);
        ArrendadorDTO testDTO = modelMapper.map(test, ArrendadorDTO.class);
        return testDTO;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public Optional<ArrendadorDTO> login(String correo, String contrasena) {
        System.out.println(correo + " " + contrasena);
        Optional<Arrendador> arrendador = repository.findByCorreoAndContrasena(correo, contrasena);
        System.out.println(arrendador);
        return arrendador.map(a -> modelMapper.map(a, ArrendadorDTO.class));
    }
    

}
