package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.entity.SolicitudArriendo;
import com.backend.backend_web.repository.SolicitudArrendamientoRepository;
import com.backend.backend_web.service.SolicitudArriendoService;

@SpringBootTest
class SolicitudArriendoServiceTest {

    @Mock
    private SolicitudArrendamientoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SolicitudArriendoService service;

    @Test
    void testGetSolicitudArriendo() {
        // Arrange
        Long id = 1L;
        SolicitudArriendo solicitud = new SolicitudArriendo();
        SolicitudArriendoDTO solicitudDTO = new SolicitudArriendoDTO();

        when(repository.findById(id)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(solicitud, SolicitudArriendoDTO.class)).thenReturn(solicitudDTO);

        // Act
        SolicitudArriendoDTO result = service.get(id);

        // Assert
        assertNotNull(result);
        assertEquals(solicitudDTO, result);

        verify(repository).findById(id);
        verify(modelMapper).map(solicitud, SolicitudArriendoDTO.class);
    }

    @Test
    void testGetSolicitudArriendo_NotFound() {
        // Arrange
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        SolicitudArriendoDTO result = service.get(id);

        // Assert
        assertNull(result);

        verify(repository).findById(id);
        verify(modelMapper, never()).map(any(), eq(SolicitudArriendoDTO.class));
    }

    @Test
    void testGetAllSolicitudArriendo() {
        // Arrange
        SolicitudArriendo solicitud1 = new SolicitudArriendo();
        SolicitudArriendo solicitud2 = new SolicitudArriendo();
        List<SolicitudArriendo> solicitudes = Arrays.asList(solicitud1, solicitud2);
        SolicitudArriendoDTO solicitudDTO1 = new SolicitudArriendoDTO();
        SolicitudArriendoDTO solicitudDTO2 = new SolicitudArriendoDTO();

        when(repository.findAll()).thenReturn(solicitudes);
        when(modelMapper.map(solicitud1, SolicitudArriendoDTO.class)).thenReturn(solicitudDTO1);
        when(modelMapper.map(solicitud2, SolicitudArriendoDTO.class)).thenReturn(solicitudDTO2);

        // Act
        List<SolicitudArriendoDTO> result = service.get();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(solicitudDTO1));
        assertTrue(result.contains(solicitudDTO2));

        verify(repository).findAll();
        verify(modelMapper).map(solicitud1, SolicitudArriendoDTO.class);
        verify(modelMapper).map(solicitud2, SolicitudArriendoDTO.class);
    }

    @Test
    void testSaveSolicitudArriendo() {
        // Arrange
        SolicitudArriendoDTO solicitudDTO = new SolicitudArriendoDTO();
        SolicitudArriendo solicitud = new SolicitudArriendo();
        solicitud.setId(1L);

        when(modelMapper.map(solicitudDTO, SolicitudArriendo.class)).thenReturn(solicitud);
        when(repository.save(solicitud)).thenReturn(solicitud);

        // Act
        SolicitudArriendoDTO result = service.save(solicitudDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(modelMapper).map(solicitudDTO, SolicitudArriendo.class);
        verify(repository).save(solicitud);
    }

    @Test
    void testUpdateSolicitudArriendo() {
        // Arrange
        SolicitudArriendoDTO solicitudDTO = new SolicitudArriendoDTO();
        solicitudDTO.setId(1L);
        SolicitudArriendo solicitud = new SolicitudArriendo();
        solicitud.setId(1L);

        when(repository.existsById(solicitudDTO.getId())).thenReturn(true);
        when(modelMapper.map(solicitudDTO, SolicitudArriendo.class)).thenReturn(solicitud);
        when(repository.save(solicitud)).thenReturn(solicitud);
        when(modelMapper.map(solicitud, SolicitudArriendoDTO.class)).thenReturn(solicitudDTO);

        // Act
        SolicitudArriendoDTO result = service.update(solicitudDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repository).existsById(solicitudDTO.getId());
        verify(modelMapper).map(solicitudDTO, SolicitudArriendo.class);
        verify(repository).save(solicitud);
        verify(modelMapper).map(solicitud, SolicitudArriendoDTO.class);
    }

@Test
    void testUpdateSolicitudArriendo_NotFound() {
        // Arrange
        SolicitudArriend
