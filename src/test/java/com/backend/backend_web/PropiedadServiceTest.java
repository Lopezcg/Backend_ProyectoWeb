package com.backend.backend_web;

import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.entity.Propiedad;
import com.backend.backend_web.repository.PropiedadRepository;
import com.backend.backend_web.service.PropiedadService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropiedadServiceTest {

    @Mock
    private PropiedadRepository propiedadRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PropiedadService propiedadService;

    private Propiedad propiedad;
    private PropiedadDTO propiedadDTO;

    @BeforeEach
    public void setUp() {
        propiedad = new Propiedad();
        propiedad.setId(1L);
        propiedad.setNombre("Casa Test");
        // Configure más atributos según sea necesario...

        propiedadDTO = new PropiedadDTO();
        propiedadDTO.setId(propiedad.getId());
        propiedadDTO.setNombre(propiedad.getNombre());
        // Copie los atributos necesarios del objeto entidad al DTO...
    }

    @Test
    public void whenGetById_thenPropiedadDTOShouldBeReturned() {
        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedad));
        when(modelMapper.map(any(Propiedad.class), eq(PropiedadDTO.class))).thenReturn(propiedadDTO);

        PropiedadDTO found = propiedadService.get(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(propiedadDTO.getId());
    }

    @Test
    public void whenGetById_thenThrowEntityNotFoundException() {
        when(propiedadRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> propiedadService.get(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Propiedad no encontrada con el ID: 1");
    }

    @Test
    public void whenGetAll_thenPropiedadDTOListShouldBeReturned() {
        when(propiedadRepository.findAll()).thenReturn(Arrays.asList(propiedad));
        when(modelMapper.map(any(Propiedad.class), eq(PropiedadDTO.class))).thenReturn(propiedadDTO);

        List<PropiedadDTO> found = propiedadService.get();

        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getId()).isEqualTo(propiedadDTO.getId());
    }

    @Test
    public void whenSave_thenPropiedadDTOShouldBeReturned() {
        when(modelMapper.map(any(PropiedadDTO.class), eq(Propiedad.class))).thenReturn(propiedad);
        when(propiedadRepository.save(any(Propiedad.class))).thenReturn(propiedad);
        when(modelMapper.map(any(Propiedad.class), eq(PropiedadDTO.class))).thenReturn(propiedadDTO);

        PropiedadDTO saved = propiedadService.save(new PropiedadDTO());

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(propiedad.getId());
    }

    @Test
    public void whenUpdate_thenPropiedadDTOShouldBeReturned() {
        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedad));
        when(modelMapper.map(any(PropiedadDTO.class), eq(Propiedad.class))).thenReturn(propiedad);
        when(propiedadRepository.save(any(Propiedad.class))).thenReturn(propiedad);
        when(modelMapper.map(any(Propiedad.class), eq(PropiedadDTO.class))).thenReturn(propiedadDTO);

        PropiedadDTO updated = propiedadService.update(propiedadDTO);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(propiedadDTO.getId());
    }

    @Test
    public void whenDelete_thenRepositoryShouldBeCalled() {
        doNothing().when(propiedadRepository).deleteById(1L);

        propiedadService.delete(1L);

        verify(propiedadRepository, times(1)).deleteById(1L);
    }
}
