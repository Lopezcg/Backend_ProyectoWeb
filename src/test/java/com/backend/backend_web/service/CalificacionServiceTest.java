package com.backend.backend_web.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.ArrayList;
import com.backend.backend_web.repository.CalificacionRepository;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.dto.CalificacionDTO;
import java.util.NoSuchElementException;

public class CalificacionServiceTest {

    private CalificacionRepository repositoryMock;
    private ModelMapper modelMapperMock;
    private CalificacionService service;

    @Before
    public void setUp() {
        repositoryMock = mock(CalificacionRepository.class);
        modelMapperMock = mock(ModelMapper.class);
        service = new CalificacionService(repositoryMock, modelMapperMock);
    }

    @Test
    public void testGetCalificaciones() {
        // Mock de CalificacionRepository.findAll()
        List<Calificacion> calificaciones = new ArrayList<>();
        calificaciones.add(new Calificacion());
        calificaciones.add(new Calificacion());
        when(repositoryMock.findAll()).thenReturn(calificaciones);
        
        // Mock de ModelMapper.map() para convertir Calificacion a CalificacionDTO
        List<CalificacionDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new CalificacionDTO());
        expectedDTOs.add(new CalificacionDTO());
        when(modelMapperMock.map(any(Calificacion.class), eq(CalificacionDTO.class)))
            .thenReturn(expectedDTOs.get(0))
            .thenReturn(expectedDTOs.get(1));
        
        // Ejecución del método get()
        List<CalificacionDTO> resultDTOs = service.get();
        
        // Verificación de resultados
        assertNotNull(resultDTOs);
        assertEquals(expectedDTOs.size(), resultDTOs.size());
        assertEquals(expectedDTOs.get(0), resultDTOs.get(0));
        assertEquals(expectedDTOs.get(1), resultDTOs.get(1));
    }
}

