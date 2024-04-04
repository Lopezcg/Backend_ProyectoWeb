package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.backend.backend_web.dto.PropiedadDTO;
import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.SolicitudArriendo;

class PropiedadDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Long id = 1L;
        String nombre = "Casa de playa";
        String descripcion = "Hermosa casa ubicada frente al mar";
        Long valor = 1000000L;
        String estado = "Disponible";
        Arrendador arrendador = new Arrendador();
        List<SolicitudArriendo> solicitudes = new ArrayList<>();

        // Act
        PropiedadDTO propiedadDTO = new PropiedadDTO();
        propiedadDTO.setId(id);
        propiedadDTO.setNombre(nombre);
        propiedadDTO.setDescripcion(descripcion);
        propiedadDTO.setValor(valor);
        propiedadDTO.setEstado(estado);
        propiedadDTO.setArrendador(arrendador);
        propiedadDTO.setSolicitudes(solicitudes);

        // Assert
        assertEquals(id, propiedadDTO.getId());
        assertEquals(nombre, propiedadDTO.getNombre());
        assertEquals(descripcion, propiedadDTO.getDescripcion());
        assertEquals(valor, propiedadDTO.getValor());
        assertEquals(estado, propiedadDTO.getEstado());
        assertEquals(arrendador, propiedadDTO.getArrendador());
        assertEquals(solicitudes, propiedadDTO.getSolicitudes());
    }
}
