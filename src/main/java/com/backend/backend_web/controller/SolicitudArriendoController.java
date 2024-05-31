package com.backend.backend_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.ArrendadorService;
import com.backend.backend_web.service.ArrendatarioService;
import com.backend.backend_web.service.SolicitudArriendoService;

@RequestMapping("/solicitudArriendo")
@RestController
public class SolicitudArriendoController {

    @Autowired
    private SolicitudArriendoService service;
    @Autowired
    private final ArrendatarioService arrendatarioService = new ArrendatarioService();
    @Autowired
    private final ArrendadorService arrendadorService = new ArrendadorService();

    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolicitudArriendoDTO> createSolicitudArriendo(
            @RequestBody SolicitudArriendoDTO solicitudArriendo, Authentication authentication) throws Exception {
        // try {
        // if (solicitudArriendo == null) {
        // return ResponseEntity.badRequest().build();
        // }
        ArrendatarioDTO arrendatario = arrendatarioService.autorizacion(authentication);
        SolicitudArriendoDTO savedSolicitud = service.save(solicitudArriendo, arrendatario);
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
    @GetMapping(value = "/get/arrendatario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SolicitudArriendoDTO>> readAllSolicitudArriendoByArrendatario(
            Authentication authentication) throws Exception {
        ArrendatarioDTO arrendatario = arrendatarioService.autorizacion(authentication);
        return ResponseEntity.ok().body(service.getSolicitudArriendoByArrendatario(arrendatario));
    }

    @CrossOrigin
    @GetMapping(value = "/get/arrendador", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SolicitudArriendoDTO>> readAllSolicitudArriendoByArrendador(
            Authentication authentication) throws Exception {
        System.out.println("ENTREE ACAAA");
        ArrendadorDTO arrendador = arrendadorService.autorizacion(authentication);
        return ResponseEntity.ok().body(service.getSolicitudArriendoByArrendador(arrendador));
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
            @RequestBody SolicitudArriendoDTO solicitudArriendo, Authentication authentication)
            throws Exception {
        // try {
        // if (solicitudArriendo == null) {
        // return ResponseEntity.badRequest().build();
        // }
        ArrendadorDTO arrendador = arrendadorService.autorizacion(authentication);
        SolicitudArriendoDTO updatedSolicitud = service.update(solicitudArriendo, arrendador);
        return ResponseEntity.ok().body(updatedSolicitud);
        // } catch (Exception e) {
        // throw new RuntimeException(e.getMessage());
        // }
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSolicitudArriendo(@PathVariable Long id, Authentication authentication)
            throws Exception {
        ArrendatarioDTO arrendatario = arrendatarioService.autorizacion(authentication);
        service.delete(id, arrendatario);
        return ResponseEntity.ok().build();
    }

}
