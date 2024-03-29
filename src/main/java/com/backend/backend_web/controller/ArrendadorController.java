package com.backend.backend_web.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
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
    private ArrendadorService service;
    @Autowired
     ModelMapper modelMapper;

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> createArrendador(@RequestBody Arrendador arrendador) {
        try {
            if (arrendador == null) {
                return ResponseEntity.badRequest().build();
            }
            Arrendador savedarrendador = service.save(arrendador);
            // Convert Arrendador to ArrendadorDTO
            ArrendadorDTO arrendadorDTO = modelMapper.map(savedarrendador, ArrendadorDTO.class);
            return ResponseEntity.ok().body(arrendadorDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArrendadorDTO> readAllArrendador() {
        return service.get();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<ArrendadorDTO> readArrendador(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArrendador(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> updateArrendador(@RequestBody ArrendadorDTO arrendador) {
        try {
            if (arrendador == null) {
                return ResponseEntity.badRequest().build();
            }
            ArrendadorDTO updatedArrendador = service.update(arrendador);
            return ResponseEntity.ok().body(updatedArrendador);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
