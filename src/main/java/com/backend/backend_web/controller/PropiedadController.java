package com.backend.backend_web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_web.entity.Propiedad;
import com.backend.backend_web.repository.PropiedadRepository;

@RequestMapping("/propiedad")
@RestController
public class PropiedadController {

    @Autowired
    private PropiedadRepository repository;

    @PostMapping("")
    public ResponseEntity<Propiedad> createPropiedad(@RequestBody Propiedad propiedad) {
        try {
            if (propiedad == null) {
                return ResponseEntity.badRequest().build();
            }
            Propiedad savedPropiedad = repository.save(propiedad);
            return ResponseEntity.ok().body(savedPropiedad);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("")
    public Iterable<Propiedad> readAllPropiedad() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propiedad> readPropiedad(@PathVariable UUID id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            return repository.findById(id)
                    .map(propiedad -> ResponseEntity.ok().body(propiedad))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Propiedad> updatePropiedad(@PathVariable UUID id, @RequestBody Propiedad propiedad) {
        if (id == null || propiedad == null) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
                .map(existingPropiedad -> {
                    // Assuming Propiedad class has setters to update fields. Adjust according to
                    // your entity.
                    existingPropiedad.setNombre(propiedad.getNombre());
                    // Set other fields from `propiedad` as needed...
                    Propiedad updatedPropiedad = repository.save(existingPropiedad);
                    return ResponseEntity.ok(updatedPropiedad);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropiedad(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
                .map(propiedad -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build(); // Successfully deleted
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Not found
    }

}
