package com.backend.backend_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.SolicitudArriendoService;

@RequestMapping("/solicitudArriendo")
@RestController
public class SolicitudArriendoController {

    @Autowired
    private SolicitudArriendoService service;

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolicitudArriendoDTO> createSolicitudArriendo(
            @RequestBody SolicitudArriendoDTO solicitudArriendo) throws IllegalArgumentException, IllegalStateException,
            DataIntegrityViolationException {
        // try {
        // if (solicitudArriendo == null) {
        // return ResponseEntity.badRequest().build();
        // }
        SolicitudArriendoDTO savedSolicitud = service.save(solicitudArriendo);
        return ResponseEntity.ok().body(savedSolicitud);
        // } catch (Exception e) {
        // throw new RuntimeException(e.getLocalizedMessage());
        // }
    }

    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SolicitudArriendoDTO>> readAllSolicitudArriendo() {
        return ResponseEntity.ok().body(service.get());
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolicitudArriendoDTO> readSolicitudArriendo(@PathVariable Long id)
            throws RegistroNoEncontradoException {
        SolicitudArriendoDTO solicitud = service.get(id);
        return ResponseEntity.ok().body(solicitud);
    }

    @CrossOrigin
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolicitudArriendoDTO> updateSolicitudArriendo(
            @RequestBody SolicitudArriendoDTO solicitudArriendo) {
        try {
            if (solicitudArriendo == null) {
                return ResponseEntity.badRequest().build();
            }
            SolicitudArriendoDTO updatedSolicitud = service.update(solicitudArriendo);
            return ResponseEntity.ok().body(updatedSolicitud);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSolicitudArriendo(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
