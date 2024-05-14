package com.backend.backend_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.PagoService;

@RequestMapping("/pago")
@RestController
public class PagoController {

    private final PagoService service;

    @Autowired
    public PagoController(PagoService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagoDTO> createPago(@RequestBody PagoDTO pagoDTO) {
        // try {
        // if (pagoDTO == null) {
        // return ResponseEntity.badRequest().build();
        // }
        PagoDTO savedPago = service.save(pagoDTO);
        return ResponseEntity.ok().body(savedPago);
        // } catch (Exception e) {
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        // }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PagoDTO>> readAllPagos() {
        try {
            return ResponseEntity.ok().body(service.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagoDTO> readPago(@PathVariable Long id) throws RegistroNoEncontradoException {
        // try {
        PagoDTO pagoDTO = service.get(id);
        // if (pagoDTO != null) {
        return ResponseEntity.ok().body(pagoDTO);
        // } else {
        // return ResponseEntity.notFound().build();
        // }
        // } catch (Exception e) {
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        // }
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePago(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
