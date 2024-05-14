package com.backend.backend_web.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.ArrendadorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/arrendador")
@RestController
public class ArrendadorController {
    @Autowired
    private final ArrendadorService service;
    private final ModelMapper modelMapper;

    public ArrendadorController(ArrendadorService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> createArrendador(@RequestBody Arrendador arrendador)
            throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        // try {
        // if (arrendador == null) {
        // return ResponseEntity.badRequest().build();
        // }
        Arrendador savedarrendador = service.save(arrendador);
        // Convert Arrendador to ArrendadorDTO
        ArrendadorDTO arrendadorDTO = modelMapper.map(savedarrendador, ArrendadorDTO.class);
        return ResponseEntity.ok().body(arrendadorDTO);
        // } catch (Exception e) {
        // throw new RuntimeException(e);
        // }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArrendadorDTO> readAllArrendador() {
        return service.get();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<ArrendadorDTO> readArrendador(@PathVariable Long id) throws RegistroNoEncontradoException {
        ArrendadorDTO arrendadorDTO = service.get(id);
        if (arrendadorDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(arrendadorDTO);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArrendador(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> updateArrendador(@RequestBody Arrendador arrendador) {
        try {
            if (arrendador == null) {
                return ResponseEntity.badRequest().build();
            }
            ArrendadorDTO test = service.update(arrendador);
            return ResponseEntity.ok().body(test);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> loginArrendador(@RequestBody Arrendador arrendador) {
        return service.login(arrendador.getCorreo(), arrendador.getContrasena())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
