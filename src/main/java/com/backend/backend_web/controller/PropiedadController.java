package com.backend.backend_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.ArrendadorService;
import com.backend.backend_web.service.PropiedadService;

@RestController
@RequestMapping("/propiedad")
public class PropiedadController {
    @Autowired
    private final PropiedadService service;
    @Autowired
    private final ArrendadorService arrendadorService;

    @Autowired
    public PropiedadController(PropiedadService service, ArrendadorService arrendadorService) {
        this.service = service;
        this.arrendadorService = arrendadorService;
    }

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropiedadDTO> createPropiedad(@RequestBody PropiedadDTO propiedad,Authentication authentication)
            throws Exception {
       
        // try {
        // if (propiedad == null) {
        // return ResponseEntity.badRequest().build();
        // }
        ArrendadorDTO arrendador = arrendadorService.autorizacion(authentication);
        PropiedadDTO savedPropiedad = service.save(propiedad,arrendador);
        return ResponseEntity.ok().body(savedPropiedad);
        // } catch (Exception e) {
        // throw new RuntimeException(e.getMessage());
        // }
    }

    // Exception check
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PropiedadDTO>> readAllPropiedad() throws Exception {
        return ResponseEntity.ok().body(service.get());
    }

    // Exception check
    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropiedadDTO> readPropiedad(@PathVariable Long id) throws RegistroNoEncontradoException {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropiedadDTO> updatePropiedad(@RequestBody PropiedadDTO propiedad)
            throws RegistroNoEncontradoException, IllegalArgumentException {
        // try {
        // if (propiedad == null) {
        // return ResponseEntity.badRequest().build();
        // }
        PropiedadDTO updatedPropiedad = service.update(propiedad);
        return ResponseEntity.ok().body(updatedPropiedad);
        // } catch (Exception e) {
        // throw new RuntimeException(e.getMessage());
        // }
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePropiedad(@PathVariable Long id) throws RegistroNoEncontradoException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}