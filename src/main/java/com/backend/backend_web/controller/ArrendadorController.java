package com.backend.backend_web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.repository.ArrendadorRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/arrendador")
@RestController
public class ArrendadorController {
    @Autowired
    private ArrendadorRepository repository;

    @PostMapping("")
    public Arrendador createArrendador(@PathVariable Arrendador arrendador) {
        try {
            Arrendador savedarrendador = repository.save(arrendador);
            return savedarrendador;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("")
    public Iterable<Arrendador> readAllArrendador() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Arrendador readArrendador(@PathVariable UUID id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteArrendador(@PathVariable UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
