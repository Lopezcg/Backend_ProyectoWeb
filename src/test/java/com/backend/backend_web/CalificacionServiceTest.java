package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;

import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.exception.RegistroNoEncontradoException;
import com.backend.backend_web.repository.CalificacionRepository;
import com.backend.backend_web.service.CalificacionService;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceTest {

    @Mock
    private CalificacionRepository repositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private CalificacionService service;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(CalificacionRepository.class);
        modelMapperMock = mock(ModelMapper.class);
        service = new CalificacionService(repositoryMock, modelMapperMock);
    }

    @Test
    void testGetCalificacionExists() throws RegistroNoEncontradoException {
        Long id = 1L;
        Calificacion calificacion = new Calificacion();
        CalificacionDTO expectedDTO = new CalificacionDTO();

        when(repositoryMock.findById(id)).thenReturn(Optional.of(calificacion));
        when(modelMapperMock.map(calificacion, CalificacionDTO.class)).thenReturn(expectedDTO);

        CalificacionDTO resultDTO = service.get(id);

        assertNotNull(resultDTO);
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    void testGetCalificacionNotFound() {
        Long id = 2L;
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RegistroNoEncontradoException.class, () -> service.get(id));

        String expectedMessage = "No se encontró una Calificación con el ID: " + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSaveCalificacion() {
        CalificacionDTO calificacionDTO = new CalificacionDTO();
        Calificacion calificacion = new Calificacion();
        calificacion.setId(1L);

        when(modelMapperMock.map(calificacionDTO, Calificacion.class)).thenReturn(calificacion);
        when(repositoryMock.save(any(Calificacion.class))).thenReturn(calificacion);

        CalificacionDTO resultDTO = service.save(calificacionDTO);

        assertNotNull(resultDTO);
        assertEquals(Long.valueOf(1L), resultDTO.getId());
    }

    @Test
    void testUpdateCalificacionExists() throws RegistroNoEncontradoException {
        Long id = 1L;
        CalificacionDTO calificacionDTOToUpdate = new CalificacionDTO();
        calificacionDTOToUpdate.setId(id);
        Calificacion existingCalificacion = new Calificacion();
        existingCalificacion.setId(id);

        when(repositoryMock.findById(id)).thenReturn(Optional.of(existingCalificacion));
        when(modelMapperMock.map(calificacionDTOToUpdate, Calificacion.class)).thenReturn(existingCalificacion);
        when(repositoryMock.save(existingCalificacion)).thenReturn(existingCalificacion);
        when(modelMapperMock.map(existingCalificacion, CalificacionDTO.class)).thenReturn(calificacionDTOToUpdate);

        CalificacionDTO updatedDTO = service.update(calificacionDTOToUpdate);

        assertNotNull(updatedDTO);
        assertEquals(id, updatedDTO.getId());
    }

    @Test
    void testDeleteCalificacion() throws RegistroNoEncontradoException {
        Long id = 1L;
        doNothing().when(repositoryMock).deleteById(id);

        service.delete(id);

        verify(repositoryMock).deleteById(id);
    }
}