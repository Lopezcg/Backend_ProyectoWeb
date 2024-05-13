package com.backend.backend_web;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.backend.backend_web.dto.SolicitudArriendoDTO;
import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.entity.Pago;
import com.backend.backend_web.entity.Propiedad;

class SolicitudArriendoDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Long id = 1L;
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now().plusDays(7);
        boolean estado = true;
        int cantidadPersonas = 3;
       

        // Act
        SolicitudArriendoDTO solicitudDTO = new SolicitudArriendoDTO();
        solicitudDTO.setId(id);
        solicitudDTO.setFechainicio(fechaInicio);
        solicitudDTO.setFechafin(fechaFin);
        solicitudDTO.setEstado(estado);
        solicitudDTO.setCantidadPersonas(cantidadPersonas);
      

        // Assert
        assertEquals(id, solicitudDTO.getId());
        assertEquals(fechaInicio, solicitudDTO.getFechainicio());
        assertEquals(fechaFin, solicitudDTO.getFechafin());
        assertEquals(estado, solicitudDTO.isEstado());
        assertEquals(cantidadPersonas, solicitudDTO.getCantidadPersonas());
        
    }
}
