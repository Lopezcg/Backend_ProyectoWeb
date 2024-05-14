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

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.ArrendadorRepository;
import com.backend.backend_web.service.ArrendadorService;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ArrendadorServiceTest {
    @Mock
    private ArrendadorRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArrendadorService service;

    @Test
    void testGetArrendadorWhenFound() throws RegistroNoEncontradoException {
        Long id = 1L;
        Arrendador arrendador = new Arrendador();
        ArrendadorDTO arrendadorDTO = new ArrendadorDTO();

        when(repository.findById(id)).thenReturn(Optional.of(arrendador));
        when(modelMapper.map(any(Arrendador.class), eq(ArrendadorDTO.class))).thenReturn(arrendadorDTO);

        ArrendadorDTO result = service.get(id);

        assertNotNull(result);
        verify(repository).findById(id);
        verify(modelMapper).map(arrendador, ArrendadorDTO.class);
    }

    @Test
    void testGetArrendadorWhenNotFound() {
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

    @Test
    void testGetAllArrendadores() {
        Arrendador arrendador1 = new Arrendador();
        Arrendador arrendador2 = new Arrendador();
        List<Arrendador> listaArrendadores = Arrays.asList(arrendador1, arrendador2);
        ArrendadorDTO arrendadorDTO1 = new ArrendadorDTO();
        ArrendadorDTO arrendadorDTO2 = new ArrendadorDTO();

        when(repository.findAll()).thenReturn(listaArrendadores);
        when(modelMapper.map(arrendador1, ArrendadorDTO.class)).thenReturn(arrendadorDTO1);
        when(modelMapper.map(arrendador2, ArrendadorDTO.class)).thenReturn(arrendadorDTO2);

        List<ArrendadorDTO> result = service.get();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(arrendadorDTO1));
        assertTrue(result.contains(arrendadorDTO2));

        verify(repository).findAll();
        verify(modelMapper).map(arrendador1, ArrendadorDTO.class);
        verify(modelMapper).map(arrendador2, ArrendadorDTO.class);
    }

    @Test
    void testSaveArrendador() {
        // Crear la instancia de Arrendador con datos para la prueba
        Arrendador arrendador = new Arrendador();
        arrendador.setStatus(0); // Supongamos que esta es la forma de establecer el estado inicial

        // Simular que el repositorio devuelve la entidad con un ID tras guardar
        Arrendador arrendadorConId = new Arrendador();
        arrendadorConId.setId(1L); // Establecer un ID como si fuera asignado por la base de datos
        when(repository.save(any(Arrendador.class))).thenReturn(arrendadorConId);

        // Suponer que modelMapper.map devuelve el mismo objeto para este caso
        when(modelMapper.map(any(Arrendador.class), eq(Arrendador.class))).thenReturn(arrendador);

        // Ejecutar el método save del servicio
        Arrendador savedArrendador = service.save(arrendador);

        // Aserciones y verificaciones
        assertNotNull(savedArrendador);
        assertNotNull(savedArrendador.getId()); // Verificar que el id se ha establecido
        assertEquals(1L, savedArrendador.getId()); // Asegurarse de que el ID es el esperado

        // Verificar interacciones con el mock
        verify(repository).save(arrendador);
        // La verificación del modelMapper puede no ser necesaria si es redundante,
        // pero si decides dejarla, asegúrate de que está configurada correctamente.
    }

    void save_ShouldSaveArrendadorAndReturnEntity() {
        // Arrange
        Arrendador arrendador = new Arrendador();
        arrendador.setId(1L);
        when(repository.save(any(Arrendador.class))).thenReturn(arrendador);

        // Act
        Arrendador result = service.save(arrendador);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        verify(repository).save(arrendador);
    }

    @Test
    void delete_ShouldInvokeDeleteById() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        // Act
        service.delete(id);

        // Assert
        verify(repository).deleteById(id);
    }

    @Test
    void update_ShouldUpdateArrendador() throws RegistroNoEncontradoException {
        // Arrange
        Long id = 1L;
        Arrendador arrendadorOriginal = new Arrendador();
        arrendadorOriginal.setId(id);
        arrendadorOriginal.setNombre("Nombre Original");

        ArrendadorDTO arrendadorDTO = new ArrendadorDTO();
        arrendadorDTO.setId(id);
        arrendadorDTO.setNombre("Nombre Nuevo");

        Arrendador arrendadorActualizado = new Arrendador();
        arrendadorActualizado.setId(id);
        arrendadorActualizado.setNombre("Nombre Nuevo");

        when(repository.findById(id)).thenReturn(Optional.of(arrendadorOriginal));
        when(modelMapper.map(any(ArrendadorDTO.class), eq(Arrendador.class))).thenReturn(arrendadorOriginal);
        when(repository.save(any(Arrendador.class))).thenReturn(arrendadorActualizado);
        when(modelMapper.map(any(Arrendador.class), eq(ArrendadorDTO.class))).thenReturn(arrendadorDTO);

        // Act
        ArrendadorDTO result = service.update(arrendadorActualizado);

        // Assert
        assertNotNull(result);
        assertEquals("Nombre Nuevo", result.getNombre());

        // Verificar que el repositorio fue llamado correctamente
        verify(repository).findById(id);
        verify(repository).save(arrendadorOriginal);
        verify(modelMapper).map(arrendadorDTO, Arrendador.class);
        verify(modelMapper).map(arrendadorActualizado, ArrendadorDTO.class);
    }

}
