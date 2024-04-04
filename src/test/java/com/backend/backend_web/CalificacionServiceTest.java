package com.backend.backend_web.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import com.backend.backend_web.repository.CalificacionRepository;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.dto.CalificacionDTO;
import com.backend.backend_web.service.CalificacionService;
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
    public void testGetCalificacionExists() {
        Long id = 1L;
        Calificacion calificacion = new Calificacion();
        Optional<Calificacion> calificacionOptional = Optional.of(calificacion);
        when(repositoryMock.findById(id)).thenReturn(calificacionOptional);
        
        CalificacionDTO expectedDTO = new CalificacionDTO();
        when(modelMapperMock.map(calificacion, CalificacionDTO.class)).thenReturn(expectedDTO);
        
        CalificacionDTO resultDTO = service.get(id);
        
        assertNotNull(resultDTO);
        assertEquals(expectedDTO, resultDTO);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCalificacionNotFound() {
        Long id = 2L;
        when(repositoryMock.findById(id)).thenReturn(Optional.empty());
        
        service.get(id);
    }
    @Test
    public void testSaveCalificacion() {
    CalificacionDTO calificacionDTO = new CalificacionDTO();
    // Configura los atributos necesarios de calificacionDTO
    calificacionDTO.setId(null); // Simula un nuevo registro

    Calificacion calificacion = new Calificacion();
    // Configura los atributos necesarios de Calificacion, si es necesario
    calificacion.setId(1L); // Simula el ID asignado por la base de datos

    when(modelMapperMock.map(calificacionDTO, Calificacion.class)).thenReturn(calificacion);
    when(repositoryMock.save(any(Calificacion.class))).thenReturn(calificacion);
    when(modelMapperMock.map(calificacion, CalificacionDTO.class)).thenReturn(calificacionDTO);

    CalificacionDTO resultDTO = service.save(calificacionDTO);

    assertNotNull(resultDTO);
    assertNotNull(resultDTO.getId());
    assertEquals(Long.valueOf(1L), resultDTO.getId());
    }
    @Test
    public void testUpdateCalificacionExists() {
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

    @Test(expected = RuntimeException.class)
    public void testUpdateCalificacionNotFound() {
        Long id = 1L;
        CalificacionDTO calificacionDTOToUpdate = new CalificacionDTO();
        calificacionDTOToUpdate.setId(id);

        when(repositoryMock.findById(id)).thenReturn(Optional.empty());

        service.update(calificacionDTOToUpdate);
    }

    @Test
    public void testDeleteCalificacion() {
        Long id = 1L;
        
        doNothing().when(repositoryMock).deleteById(id);
        
        service.delete(id);
        
        verify(repositoryMock, times(1)).deleteById(id);
    }



}
