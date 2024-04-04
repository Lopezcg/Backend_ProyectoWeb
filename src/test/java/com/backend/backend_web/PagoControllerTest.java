package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend_web.controller.PagoController;
import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.service.PagoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class PagoControllerTest {

    @Mock
    private PagoService service;

    @InjectMocks
    private PagoController controller;

    @Test
    void testCreatePago() {
        // Arrange
        PagoDTO pagoDTO = new PagoDTO();
        PagoDTO savedPagoDTO = new PagoDTO();

        when(service.save(any(PagoDTO.class))).thenReturn(savedPagoDTO);

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.createPago(pagoDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(savedPagoDTO, responseEntity.getBody());

        verify(service).save(pagoDTO);
    }

    @Test
    void testCreatePago_NullInput() {
        // Arrange
        PagoDTO pagoDTO = null;

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.createPago(pagoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(service, never()).save(any(PagoDTO.class));
    }

    @Test
    void testReadAllPagos() {
        // Arrange
        List<PagoDTO> pagos = Arrays.asList(new PagoDTO(), new PagoDTO());

        when(service.get()).thenReturn(pagos);

        // Act
        ResponseEntity<List<PagoDTO>> responseEntity = controller.readAllPagos();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(pagos, responseEntity.getBody());

        verify(service).get();
    }

    @Test
    void testReadPago() {
        // Arrange
        Long id = 1L;
        PagoDTO pagoDTO = new PagoDTO();

        when(service.get(id)).thenReturn(pagoDTO);

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.readPago(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(pagoDTO, responseEntity.getBody());

        verify(service).get(id);
    }

    @Test
    void testReadPago_NotFound() {
        // Arrange
        Long id = 1L;

        when(service.get(id)).thenReturn(null);

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.readPago(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(service).get(id);
    }

    @Test
    void testDeletePago() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<?> responseEntity = controller.deletePago(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(service).delete(id);
    }

    @Test
    void testUpdatePago() {
        // Arrange
        PagoDTO pagoDTO = new PagoDTO();
        PagoDTO updatedPagoDTO = new PagoDTO();

        when(service.update(any(PagoDTO.class))).thenReturn(updatedPagoDTO);

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.updatePago(pagoDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedPagoDTO, responseEntity.getBody());

        verify(service).update(pagoDTO);
    }

    @Test
    void testUpdatePago_NullInput() {
        // Arrange
        PagoDTO pagoDTO = null;

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.updatePago(pagoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(service, never()).update(any(PagoDTO.class));
    }

    @Test
    void testCreatePago_Exception() {
        // Arrange
        PagoDTO pagoDTO = new PagoDTO();
        when(service.save(any(PagoDTO.class))).thenThrow(new RuntimeException("Error saving pago"));

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.createPago(pagoDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(service).save(pagoDTO);
    }

    @Test
    void testReadAllPagos_Exception() {
        // Arrange
        when(service.get()).thenThrow(new RuntimeException("Error getting pagos"));

        // Act
        ResponseEntity<List<PagoDTO>> responseEntity = controller.readAllPagos();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(service).get();
    }

    @Test
    void testReadPago_Exception() {
        // Arrange
        Long id = 1L;
        when(service.get(id)).thenThrow(new RuntimeException("Error getting pago with id: " + id));

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.readPago(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(service).get(id);
    }

    @Test
    void testDeletePago_Exception() {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Error deleting pago with id: " + id)).when(service).delete(id);

        // Act
        ResponseEntity<?> responseEntity = controller.deletePago(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(service).delete(id);
    }

    @Test
    void testUpdatePago_Exception() {
        // Arrange
        PagoDTO pagoDTO = new PagoDTO();
        when(service.update(any(PagoDTO.class))).thenThrow(new RuntimeException("Error updating pago"));

        // Act
        ResponseEntity<PagoDTO> responseEntity = controller.updatePago(pagoDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(service).update(pagoDTO);
    }
}