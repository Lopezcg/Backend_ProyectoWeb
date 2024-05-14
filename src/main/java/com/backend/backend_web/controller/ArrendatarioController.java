package com.backend.backend_web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.service.ArrendatarioService;
import com.backend.backend_web.service.JWTTokenService;

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
    private ModelMapper modelMapper;
    @Autowired
    JWTTokenService jwtTokenService;

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> createArrendatario(@RequestBody Arrendatario ArrendadorDTO) {
        try {
            if (ArrendadorDTO == null) {
                return ResponseEntity.badRequest().build();
            }
            Arrendatario savedarrendatario = service.save(ArrendadorDTO);
            // Convert Arrendador to ArrendadorDTO
            ArrendadorDTO test = modelMapper.map(savedarrendatario, ArrendadorDTO.class);

            return ResponseEntity.ok().body(test);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArrendadorDTO> readAllArrendatario() {
        return service.get();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrendadorDTO> readArrendatario(@PathVariable Long id) {
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
    public ResponseEntity<ArrendadorDTO> updateArrendatario(@RequestBody Arrendatario arrendatario) {
        try {
            if (arrendatario == null) {
                return ResponseEntity.badRequest().build();
            }
            ArrendadorDTO updatedarrendatario = service.update(arrendatario);
            return ResponseEntity.ok().body(updatedarrendatario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginArrendador(@RequestBody Arrendatario arrendatario) {
        try {
            return service.login(arrendatario.getCorreo(), arrendatario.getContrasena())
                    .map((ArrendadorDTO arrendadorDTO) -> {
                        String token = jwtTokenService.generarToken(arrendadorDTO);
                        System.out.println("TOKEN: " + token);
                        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
