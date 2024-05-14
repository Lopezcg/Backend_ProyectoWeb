package com.backend.backend_web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.backend.backend_web.controller.ArrendadorController;
import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.ArrendadorService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ArrendadorControllerTest {

    @Mock
    private ArrendadorService service;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArrendadorController controller;

    private Arrendador arrendador;
    private ArrendadorDTO arrendadorDTO;
    private Long arrendadorId = 1L;

    @BeforeEach
    void setUp() {
        arrendador = new Arrendador();
        arrendadorDTO = new ArrendadorDTO();
        // Setup mock behaviors and data
    }

    @Test
    void testCreateArrendador_Success() {
        when(service.save(any(Arrendador.class))).thenReturn(arrendador);
        when(modelMapper.map(any(Arrendador.class), eq(ArrendadorDTO.class))).thenReturn(arrendadorDTO);

        ResponseEntity<ArrendadorDTO> response = controller.createArrendador(arrendador);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateArrendador_Failure() {
        ResponseEntity<ArrendadorDTO> response = controller.createArrendador(null);

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateArrendador_Exception() {
        when(service.save(any(Arrendador.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.createArrendador(arrendador));
    }

    @Test
    void testReadAllArrendador_Success() {
        List<ArrendadorDTO> list = new ArrayList<>();
        list.add(arrendadorDTO);
        when(service.get()).thenReturn(list);

        List<ArrendadorDTO> result = controller.readAllArrendador();

        assertFalse(result.isEmpty());
        assertEquals(arrendadorDTO, result.get(0));
    }

    @Test
    void testReadArrendador_Success() throws RegistroNoEncontradoException {
        when(service.get(arrendadorId)).thenReturn(arrendadorDTO);

        ResponseEntity<ArrendadorDTO> response = controller.readArrendador(arrendadorId);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testReadArrendador_NotFound() throws RegistroNoEncontradoException {
        when(service.get(arrendadorId)).thenReturn(null);

        ResponseEntity<ArrendadorDTO> response = controller.readArrendador(arrendadorId);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateArrendador_Success() throws RegistroNoEncontradoException {
        when(service.update(any(Arrendador.class))).thenReturn(arrendadorDTO);

        ResponseEntity<ArrendadorDTO> response = controller.updateArrendador(arrendador);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateArrendador_Failure() throws RegistroNoEncontradoException {
        ResponseEntity<ArrendadorDTO> response = controller.updateArrendador(null);

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateArrendador_Exception() throws RegistroNoEncontradoException {
        when(service.update(any(Arrendador.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.updateArrendador(arrendador));
    }

    @Test
    void testDeleteArrendador_Success() throws RegistroNoEncontradoException {
        doNothing().when(service).delete(arrendadorId);

        ResponseEntity<?> response = controller.deleteArrendador(arrendadorId);

        assertEquals(OK, response.getStatusCode());
        verify(service, times(1)).delete(arrendadorId);
    }

    @Test
    void testDeleteArrendador_Exception() throws RegistroNoEncontradoException {
        doThrow(RuntimeException.class).when(service).delete(arrendadorId);

        assertThrows(RuntimeException.class, () -> controller.deleteArrendador(arrendadorId));
    }
}
