package com.backend.backend_web.controller;
import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.ArrendatarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RequestMapping("/arrendatario")
@RestController
public class ArrendatarioController {
    @Autowired
    private ArrendatarioRepository repository;
    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Arrendatario createArrendatario(@RequestBody Arrendatario arrendatario) {
        try {
            Arrendatario savedarrendatario = repository.save(arrendatario);
            return savedarrendatario;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Arrendatario> readAllArrendatario() {
        return repository.findAll();
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Arrendatario> readArrendatario(@PathVariable Long id) {
        Arrendatario arrendatario= repository.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException("No existe arrendatario con id: " + id));
            return ResponseEntity.ok(arrendatario);
    }
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Arrendatario> deleteArrendatario(@PathVariable Long id) {
        Arrendatario arrendatario= repository.findById(id)
        .orElseThrow(() -> new RegistroNoEncontradoException("No existe arrendatario con id: " + id));
        repository.delete(arrendatario);
        return ResponseEntity.ok(arrendatario);
    }
    

}
