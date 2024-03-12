package com.backend.backend_web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.ArrendatarioDTO;

import com.backend.backend_web.service.ArrendatarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RequestMapping("/arrendatario")
@RestController
public class ArrendatarioController {
    @Autowired
    private ArrendatarioService service;

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendatarioDTO> createArrendatario(@RequestBody ArrendatarioDTO arrendatarioDTO) {
        try {
            if (arrendatarioDTO == null) {
                return ResponseEntity.badRequest().build();
            }
            ArrendatarioDTO savedarrendatario = service.save(arrendatarioDTO);
            return ResponseEntity.ok().body(savedarrendatario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArrendatarioDTO> readAllArrendatario() {
        return service.get();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendatarioDTO> readArrendatario(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteArrendatario(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendatarioDTO> updateArrendatario(@RequestBody ArrendatarioDTO arrendatario) {
        try {
            if (arrendatario == null) {
                return ResponseEntity.badRequest().build();
            }
            ArrendatarioDTO updatedarrendatario = service.update(arrendatario);
                return ResponseEntity.ok().body(updatedarrendatario);  
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
