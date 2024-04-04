package com.backend.backend_web.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.backend.backend_web.dto.PagoDTO;
import com.backend.backend_web.entity.Pago;
import com.backend.backend_web.repository.PagoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PagoService service;

    @BeforeEach
    void setUp() {
        // Mockito annotations are initialized before each test
    }

    @Test
    void testGetByIdWhenFound() {
        Long id = 1L;
        Pago pago = new Pago();
        PagoDTO pagoDTO = new PagoDTO();

        when(repository.findById(id)).thenReturn(Optional.of(pago));
        when(modelMapper.map(pago, PagoDTO.class)).thenReturn(pagoDTO);

        PagoDTO result = service.get(id);

        assertNotNull(result);
    }

    @Test
    void testGetByIdWhenNotFound() {
        Long id = 2L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        PagoDTO result = service.get(id);

        assertNull(result);
    }

    @Test
    void testGetAll() {
        Pago pago1 = new Pago();
        Pago pago2 = new Pago();
        List<Pago> pagos = Arrays.asList(pago1, pago2);
        PagoDTO pagoDTO1 = new PagoDTO();
        PagoDTO pagoDTO2 = new PagoDTO();

        when(repository.findAll()).thenReturn(pagos);
        when(modelMapper.map(pago1, PagoDTO.class)).thenReturn(pagoDTO1);
        when(modelMapper.map(pago2, PagoDTO.class)).thenReturn(pagoDTO2);

        List<PagoDTO> result = service.get();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        PagoDTO pagoDTO = new PagoDTO();
        Pago pago = new Pago();
        pago.setId(1L);

        when(modelMapper.map(pagoDTO, Pago.class)).thenReturn(pago);
        when(repository.save(any(Pago.class))).thenReturn(pago);

        PagoDTO result = service.save(pagoDTO);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test
    void testUpdateWhenFound() {
        Long id = 1L;
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setId(id);
        Pago pago = new Pago();
        pago.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(pago));
        when(modelMapper.map(pagoDTO, Pago.class)).thenReturn(pago);
        when(repository.save(pago)).thenReturn(pago);
        when(modelMapper.map(pago, PagoDTO.class)).thenReturn(pagoDTO);

        PagoDTO result = service.update(pagoDTO);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testUpdateWhenNotFound() {
        Long id = 2L;
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setId(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> service.update(pagoDTO));

        assertEquals("Registro no encontrado", exception.getMessage());
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
