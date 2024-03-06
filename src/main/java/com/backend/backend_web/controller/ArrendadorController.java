package com.backend.backend_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.repository.ArrendadorRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/arrendador")
@Controller
public class ArrendadorController {
    @Autowired
    private ArrendadorRepository repository;

    @PostMapping("/create")
    public Arrendador createArrendador(@RequestBody Arrendador arrendador) {
        try {
            Arrendador savedarrendador = repository.save(arrendador);
            return savedarrendador;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get")
    public Arrendador readArrendador(@RequestParam Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
