package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.repository.ArrendadorRepository;
import com.backend.backend_web.service.ArrendadorService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class ArrendadorServiceTest {
    @Mock
    private ArrendadorRepository repository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private ArrendadorService service;
    
    @Test
    public void testGetArrendadorWhenFound() {
        Long id = 1L;
        Arrendador arrendador = new Arrendador(); // Suponiendo que tienes un constructor predeterminado
        ArrendadorDTO arrendadorDTO = new ArrendadorDTO(); // Suponiendo que también tienes un constructor predeterminado
        
        when(repository.findById(id)).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(any(Arrendador.class), eq(ArrendadorDTO.class))).thenReturn(arrendadorDTO);
        
        ArrendadorDTO result = service.get(id);
        
        assertNotNull(result);
        verify(repository).findById(id);
        verify(modelMapper).map(arrendador, ArrendadorDTO.class);
    }
    
    @Test
    public void testGetArrendadorWhenNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            service.get(id);
        });
        
        String expectedMessage = "Arrendador no encontrado con el ID: " + id;
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository).findById(id);
    }
}
