package com.backend.backend_web.controller;

   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.service.PropiedadService;

@RestController
@RequestMapping("/propiedad")
public class PropiedadController {
    
    private final PropiedadService service;

    @Autowired
    public PropiedadController(PropiedadService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropiedadDTO> createPropiedad(@RequestBody PropiedadDTO propiedad) {
        try {
            if (propiedad == null) {
                return ResponseEntity.badRequest().build();
            }
            PropiedadDTO savedPropiedad = service.save(propiedad);
            return ResponseEntity.ok().body(savedPropiedad);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PropiedadDTO>> readAllPropiedad() {
        return ResponseEntity.ok().body(service.get());
    }
    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropiedadDTO> readPropiedad(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @CrossOrigin
    @PutMapping(value = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropiedadDTO> updatePropiedad(@PathVariable Long id, @RequestBody PropiedadDTO propiedad) {
        try {
            if (propiedad == null) {
                return ResponseEntity.badRequest().build();
            }
            PropiedadDTO updatedPropiedad = service.update(propiedad);
            return ResponseEntity.ok().body(updatedPropiedad);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePropiedad(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

