package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.backend.backend_web.controller.PropiedadController;
import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.service.PropiedadService;

@SpringBootTest
class PropiedadControllerTest {

    @Mock
    private PropiedadService service;

    @InjectMocks
    private PropiedadController controller;

    @Test
    void testCreatePropiedad() {
        // Arrange
        PropiedadDTO propiedadDTO = new PropiedadDTO();
        PropiedadDTO savedPropiedadDTO = new PropiedadDTO();
        when(service.save(propiedadDTO)).thenReturn(savedPropiedadDTO);

        // Act
        ResponseEntity<PropiedadDTO> responseEntity = controller.createPropiedad(propiedadDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(savedPropiedadDTO, responseEntity.getBody());

        verify(service).save(propiedadDTO);
    }

    @Test
    void testCreatePropiedad_NullInput() {
        // Act
        ResponseEntity<PropiedadDTO> responseEntity = controller.createPropiedad(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verifyNoInteractions(service);
    }

    // @Test
    // void testReadAllPropiedad() {
    // // Arrange
    // Iterable<PropiedadDTO> propiedadesDTO = mock(Iterable.class);
    // List<PropiedadDTO> propiedadesList =
    // StreamSupport.stream(propiedadesDTO.spliterator(), false)
    // .collect(Collectors.toList());
    // when(service.get()).thenReturn(propiedadesList);

    // // Act
    // ResponseEntity<Iterable<PropiedadDTO>> responseEntity =
    // controller.readAllPropiedad();

    // // Assert
    // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // assertEquals(propiedadesDTO, responseEntity.getBody());

    // verify(service).get();
    // }

    // @Test
    // void testUpdatePropiedad() {
    // // Arrange
    // Long id = 1L;
    // PropiedadDTO propiedadDTO = new PropiedadDTO();
    // PropiedadDTO updatedPropiedadDTO = new PropiedadDTO();
    // when(service.update(eq(id),
    // eq(propiedadDTO))).thenReturn(updatedPropiedadDTO);

    // // Act
    // ResponseEntity<PropiedadDTO> responseEntity = controller.updatePropiedad(id,
    // propiedadDTO);

    // // Assert
    // assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // assertEquals(updatedPropiedadDTO, responseEntity.getBody());

    // verify(service).update(id, propiedadDTO);
    // }

    @Test
    void testUpdatePropiedad_NullInput() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<PropiedadDTO> responseEntity = controller.updatePropiedad(id, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verifyNoInteractions(service);
    }

    @Test
    void testDeletePropiedad() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<?> responseEntity = controller.deletePropiedad(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(service).delete(id);
    }
}
