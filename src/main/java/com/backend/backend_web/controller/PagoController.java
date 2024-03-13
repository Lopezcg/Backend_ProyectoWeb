package com.backend.backend_web.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.service.PagoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RequestMapping("/pago")
@RestController
public class PagoController {
@Autowired
    private PagoService service;

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagoDTO> createPago(@RequestBody PagoDTO pagoDTO) {
        try {
            if (pagoDTO == null) {
                return ResponseEntity.badRequest().build();
            }
            PagoDTO savedPago = service.save(pagoDTO);
            return ResponseEntity.ok().body(savedPago);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PagoDTO> readAllPagos() {
        return service.get();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagoDTO> readPago(@PathVariable Long id) {
        PagoDTO pagoDTO = service.get(id);
        if (pagoDTO != null) {
            return ResponseEntity.ok().body(pagoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePago(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagoDTO> updatePago(@RequestBody PagoDTO pagoDTO) {
        try {
            if (pagoDTO == null) {
                return ResponseEntity.badRequest().build();
            }
            PagoDTO updatedPago = service.update(pagoDTO);
            return ResponseEntity.ok().body(updatedPago);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
