package com.backend.backend_web.controller;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.service.CalificacionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/calificacion")
@RestController
public class CalificacionController {
    @Autowired
    private CalificacionService service;

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacionDTO> createPropiedad(@RequestBody CalificacionDTO propiedad) {
        try {
            if (propiedad == null) {
                return ResponseEntity.badRequest().build();
            }
            CalificacionDTO savedPropiedad = service.save(propiedad);
            return ResponseEntity.ok().body(savedPropiedad);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CalificacionDTO> readAllPropiedad() {
        return service.get();
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacionDTO> readPropiedad(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacionDTO> updatePropiedad(@PathVariable UUID id, @RequestBody CalificacionDTO propiedad) {
        try {
            if (propiedad == null) {
                return ResponseEntity.badRequest().build();
            }
            CalificacionDTO updatedPropiedad = service.update(propiedad);
            return ResponseEntity.ok().body(updatedPropiedad);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePropiedad(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
