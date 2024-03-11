package com.backend.backend_web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.repository.ArrendadorRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/arrendador")
@RestController
public class ArrendadorController {
    @Autowired
    private ArrendadorRepository repository;

    @PostMapping("")
    public ResponseEntity<Arrendador> createArrendador(@RequestBody Arrendador arrendador) {
        try {
            if (arrendador == null) {
                return ResponseEntity.badRequest().build();
            }
            Arrendador savedarrendador = repository.save(arrendador);
            return ResponseEntity.ok().body(savedarrendador);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("")
    public Iterable<Arrendador> readAllArrendador() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arrendador> readArrendador(@PathVariable UUID id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            return repository.findById(id)
                    .map(arrendador -> ResponseEntity.ok().body(arrendador))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArrendador(@PathVariable UUID id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("id is null");
            }
            return repository.findById(id)
                    .map(arrendador -> {
                        repository.deleteById(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
