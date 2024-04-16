package com.backend.backend_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.service.ArrendatarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RequestMapping("/arrendatario")
@RestController
public class ArrendatarioController {
    @Autowired
    private ArrendatarioService service;
    @Autowired
    ModelMapper modelMapper;
    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendatarioDTO> createArrendatario(@RequestBody Arrendatario arrendatarioDTO) {
        try {
            if (arrendatarioDTO == null) {
                return ResponseEntity.badRequest().build();
            }
            Arrendatario savedarrendatario = service.save(arrendatarioDTO);
            // Convert Arrendador to ArrendadorDTO
            ArrendatarioDTO test = modelMapper.map(savedarrendatario, ArrendatarioDTO.class);

            return ResponseEntity.ok().body(test);
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
    public ResponseEntity<ArrendatarioDTO> readArrendatario(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteArrendatario(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendatarioDTO> updateArrendatario(@RequestBody Arrendatario arrendatario) {
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
    @CrossOrigin
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendatarioDTO> loginArrendador(@RequestBody Arrendatario arrendatario) {
        return service.login(arrendatario.getCorreo(), arrendatario.getContrasena())
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
