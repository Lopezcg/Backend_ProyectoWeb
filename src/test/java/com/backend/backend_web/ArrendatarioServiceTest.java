package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend_web.dto.ArrendatarioDTO;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.repository.ArrendatarioRepository;
import com.backend.backend_web.service.ArrendatarioService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class ArrendatarioServiceTest {

    @Mock
    private ArrendatarioRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArrendatarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetById() {
        // Arrange
        Long id = 1L;
        Arrendatario arrendatario = new Arrendatario();
        arrendatario.setId(id);
        ArrendatarioDTO arrendatarioDTO = new ArrendatarioDTO();
        arrendatarioDTO.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(arrendatario));
        when(modelMapper.map(arrendatario, ArrendatarioDTO.class)).thenReturn(arrendatarioDTO);

        // Act
        ArrendatarioDTO result = service.get(id);

        // Assert
        assertNotNull(result);
        assertEquals(arrendatarioDTO, result);
        verify(repository).findById(id);
        verify(modelMapper).map(arrendatario, ArrendatarioDTO.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetById_EntityNotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> {
            service.get(id);
        });
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Arrendatario> arrendatarios = new ArrayList<>();
        arrendatarios.add(new Arrendatario());
        arrendatarios.add(new Arrendatario());

        List<ArrendatarioDTO> arrendatariosDTO = arrendatarios.stream()
                .map(arrendatario -> new ArrendatarioDTO())
                .collect(Collectors.toList());

        when(repository.findAll()).thenReturn(arrendatarios);
        when(modelMapper.map(any(Arrendatario.class), eq(ArrendatarioDTO.class)))
                .thenReturn(new ArrendatarioDTO());

        // Act
        List<ArrendatarioDTO> result = service.get();

        // Assert
        assertNotNull(result);
        assertEquals(arrendatariosDTO.size(), result.size());
        verify(repository).findAll();
        verify(modelMapper, times(arrendatarios.size())).map(any(Arrendatario.class), eq(ArrendatarioDTO.class));
    }

    // @Test
    // void testSave() {
    // // Arrange
    // Arrendatario arrendatario = new Arrendatario();
    // ArrendatarioDTO arrendatarioDTO = new ArrendatarioDTO();

    // when(modelMapper.map(arrendatarioDTO,
    // Arrendatario.class)).thenReturn(arrendatario);
    // when(repository.save(arrendatario)).thenReturn(arrendatario);
    // when(modelMapper.map(arrendatario,
    // ArrendatarioDTO.class)).thenReturn(arrendatarioDTO);

    // // Act
    // ArrendatarioDTO result = service.save(arrendatarioDTO);

    // // Assert
    // assertNotNull(result);
    // verify(modelMapper).map(arrendatarioDTO, Arrendatario.class);
    // verify(repository).save(arrendatario);
    // verify(modelMapper).map(arrendatario, ArrendatarioDTO.class);
    // }

    // @Test
    // void testUpdate() {
    // // Arrange
    // Long id = 1L;
    // ArrendatarioDTO arrendatarioDTO = new ArrendatarioDTO();
    // arrendatarioDTO.setId(id);
    // Arrendatario arrendatario = new Arrendatario();
    // arrendatario.setId(id);

    // when(repository.findById(id)).thenReturn(Optional.of(arrendatario));
    // when(repository.save(arrendatario)).thenReturn(arrendatario);
    // when(modelMapper.map(any(ArrendatarioDTO.class),
    // eq(Arrendatario.class))).thenReturn(arrendatario);
    // when(modelMapper.map(arrendatario,
    // ArrendatarioDTO.class)).thenReturn(arrendatarioDTO);

    // // Act
    // ArrendatarioDTO result = service.update(arrendatarioDTO);

    // // Assert
    // assertNotNull(result);
    // verify(repository).findById(id);
    // verify(repository).save(arrendatario);
    // verify(modelMapper).map(arrendatarioDTO, Arrendatario.class);
    // verify(modelMapper).map(arrendatario, ArrendatarioDTO.class);
    // }

    // @Test
    // void testUpdate_EntityNotFound() {
    // // Arrange
    // Long id = 1L;
    // ArrendatarioDTO arrendatarioDTO = new ArrendatarioDTO();
    // arrendatarioDTO.setId(id);

    // when(repository.findById(id)).thenReturn(Optional.empty());

    // // Act + Assert
    // assertThrows(EntityNotFoundException.class, () -> {
    // service.update(arrendatarioDTO);
    // });
    // }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        service.delete(id);

        // Assert
        verify(repository).deleteById(id);
    }
}
