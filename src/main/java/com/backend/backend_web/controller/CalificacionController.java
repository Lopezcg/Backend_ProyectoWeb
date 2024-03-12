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
    public ResponseEntity<CalificacionDTO> createCalificacion(@RequestBody CalificacionDTO Calificacion) {
        try {
            if (Calificacion == null) {
                return ResponseEntity.badRequest().build();
            }
            CalificacionDTO savedCalificacion = service.save(Calificacion);
            return ResponseEntity.ok().body(savedCalificacion);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<CalificacionDTO>> readCalificacion() {
        return ResponseEntity.ok().body(service.get());
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacionDTO> updateCalificacion(@PathVariable UUID id, @RequestBody CalificacionDTO Calificacion) {
        try {
            if (Calificacion == null) {
                return ResponseEntity.badRequest().build();
            }
            CalificacionDTO updatedCalificacion = service.update(Calificacion);
            return ResponseEntity.ok().body(updatedCalificacion);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCalificacion(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
