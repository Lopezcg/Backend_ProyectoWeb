package com.backend.backend_web.controller;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.ArrendatarioRepository;
import com.backend.backend_web.service.ArrendatarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private ArrendatarioService service;
    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrendatarioDTO createArrendatario(@RequestBody ArrendatarioDTO arrendatarioDTO) {
        try {
            ArrendatarioDTO savedarrendatario = service.save(arrendatarioDTO);
            return savedarrendatario;
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
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrendatarioDTO readArrendatario(@PathVariable UUID id) {
        return service.get(id);
    }
    @CrossOrigin
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteArrendatario(@PathVariable UUID id) {
        service.delete(id);
    }
    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrendatarioDTO updateArrendatario(@RequestBody ArrendatarioDTO arrendatario) {
        try {
            ArrendatarioDTO updatedarrendatario = service.save(arrendatario);
            return updatedarrendatario;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

}
