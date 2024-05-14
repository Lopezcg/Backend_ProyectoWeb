package com.backend.backend_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
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
    public ResponseEntity<CalificacionDTO> createCalificacion(@RequestBody CalificacionDTO Calificacion)
            throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        CalificacionDTO savedCalificacion = service.save(Calificacion);
        return ResponseEntity.ok().body(savedCalificacion);
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<CalificacionDTO>> readAllCalificacion() {
        return ResponseEntity.ok().body(service.get());
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionDTO> readCalificacion(@PathVariable Long id)
            throws RegistroNoEncontradoException {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @GetMapping("/propiedad/{id}")
    public ResponseEntity<Iterable<CalificacionDTO>> readCalificacionByPropiedad(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getCalificacionByPropiedad(id));
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacionDTO> updateCalificacion(@RequestBody CalificacionDTO calificacion)
            throws RegistroNoEncontradoException, IllegalArgumentException {
        // try {
        // if (calificacion == null) {
        // return ResponseEntity.badRequest().build();
        // }
        CalificacionDTO updatedCalificacion = service.update(calificacion);
        return ResponseEntity.ok().body(updatedCalificacion);
        // } catch (Exception e) {
        // throw new RuntimeException(e.getLocalizedMessage());
        // }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalificacion(@PathVariable Long id) throws RegistroNoEncontradoException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}