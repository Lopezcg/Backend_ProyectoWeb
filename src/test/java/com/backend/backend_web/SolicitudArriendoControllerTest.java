package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend_web.controller.SolicitudArriendoController;
import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.service.SolicitudArriendoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class SolicitudArriendoControllerTest {

    @Mock
    private SolicitudArriendoService service;

    @InjectMocks
    private SolicitudArriendoController controller;

    @Test
    void testCreateSolicitudArriendo() {
        // Arrange
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();
        SolicitudArriendoDTO savedSolicitudArriendoDTO = new SolicitudArriendoDTO();

        when(service.save(any(SolicitudArriendoDTO.class))).thenReturn(savedSolicitudArriendoDTO);

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.createSolicitudArriendo(solicitudArriendoDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(savedSolicitudArriendoDTO, responseEntity.getBody());

        verify(service).save(solicitudArriendoDTO);
    }

    @Test
    void testCreateSolicitudArriendo_NullInput() {
        // Arrange
        SolicitudArriendoDTO solicitudArriendoDTO = null;

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.createSolicitudArriendo(solicitudArriendoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(service, never()).save(any(SolicitudArriendoDTO.class));
    }

    @Test
    void testReadAllSolicitudArriendo() {
        // Arrange
        List<SolicitudArriendoDTO> solicitudes = Arrays.asList(new SolicitudArriendoDTO(), new SolicitudArriendoDTO());

        when(service.get()).thenReturn(solicitudes);

        // Act
        ResponseEntity<List<SolicitudArriendoDTO>> responseEntity = controller.readAllSolicitudArriendo();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(solicitudes, responseEntity.getBody());

        verify(service).get();
    }

    @Test
    void testReadSolicitudArriendo() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();

        when(service.get(id)).thenReturn(solicitudArriendoDTO);

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.readSolicitudArriendo(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(solicitudArriendoDTO, responseEntity.getBody());

        verify(service).get(id);
    }

    @Test
    void testReadSolicitudArriendo_NotFound() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;

        when(service.get(id)).thenReturn(null);

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.readSolicitudArriendo(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(service).get(id);
    }

    @Test
    void testUpdateSolicitudArriendo() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();
        solicitudArriendoDTO.setId(id);
        SolicitudArriendoDTO updatedSolicitudArriendoDTO = new SolicitudArriendoDTO();

        when(service.update(eq(solicitudArriendoDTO))).thenReturn(updatedSolicitudArriendoDTO);

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.updateSolicitudArriendo(
                solicitudArriendoDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedSolicitudArriendoDTO, responseEntity.getBody());

        verify(service).update(solicitudArriendoDTO);
    }

    @Test
    void testUpdateSolicitudArriendo_NullInput() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        SolicitudArriendoDTO solicitudArriendoDTO = null;

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.updateSolicitudArriendo(
                solicitudArriendoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(service, never()).update(any(SolicitudArriendoDTO.class));
    }

    @Test
    void testUpdateSolicitudArriendo_InvalidId() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();
        solicitudArriendoDTO.setId(id + 1);

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.updateSolicitudArriendo(
                solicitudArriendoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(service, never()).update(any(SolicitudArriendoDTO.class));
    }

    @Test
    void testUpdateSolicitudArriendo_NotFound() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();
        solicitudArriendoDTO.setId(id);

        when(service.update(eq(solicitudArriendoDTO))).thenReturn(null);

        // Act
        ResponseEntity<SolicitudArriendoDTO> responseEntity = controller.updateSolicitudArriendo(
                solicitudArriendoDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(service).update(solicitudArriendoDTO);
    }

    @Test
    void testDeleteSolicitudArriendo() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<?> responseEntity = controller.deleteSolicitudArriendo(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(service).delete(id);
    }

    @Test
    void testDeleteSolicitudArriendo_NotFound() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Error deleting solicitud with id: " + id)).when(service).delete(id);

        // Act
        ResponseEntity<?> responseEntity = controller.deleteSolicitudArriendo(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(service).delete(id);
    }

    @Test
    void testCreateSolicitudArriendo_Exception() {
        // Arrange
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();
        when(service.save(any(SolicitudArriendoDTO.class))).thenThrow(new RuntimeException("Error saving solicitud"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.createSolicitudArriendo(solicitudArriendoDTO));
    }

    @Test
    void testUpdateSolicitudArriendo_Exception() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        SolicitudArriendoDTO solicitudArriendoDTO = new SolicitudArriendoDTO();
        solicitudArriendoDTO.setId(id);
        when(service.update(eq(solicitudArriendoDTO))).thenThrow(new RuntimeException("Error updating solicitud"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.updateSolicitudArriendo(solicitudArriendoDTO));
    }
}
