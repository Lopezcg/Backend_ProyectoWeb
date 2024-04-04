package com.backend.backend_web;

import com.backend.backend_web.dto.ArrendadorDTO;
import com.backend.backend_web.entity.Propiedad;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArrendadorDTOTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Arrange
        Long id = 1L;
        String nombre = "John";
        String apellido = "Doe";
        String correo = "john.doe@example.com";
        String telefono = "+123456789";
        List<Propiedad> propiedades = new ArrayList<>();

        // Act
        ArrendadorDTO arrendadorDTO = new ArrendadorDTO();
        arrendadorDTO.setId(id);
        arrendadorDTO.setNombre(nombre);
        arrendadorDTO.setApellido(apellido);
        arrendadorDTO.setCorreo(correo);
        arrendadorDTO.setTelefono(telefono);
        arrendadorDTO.setPropiedades(propiedades);

        // Assert
        assertNotNull(arrendadorDTO);
        assertEquals(id, arrendadorDTO.getId());
        assertEquals(nombre, arrendadorDTO.getNombre());
        assertEquals(apellido, arrendadorDTO.getApellido());
        assertEquals(correo, arrendadorDTO.getCorreo());
        assertEquals(telefono, arrendadorDTO.getTelefono());
        assertEquals(propiedades, arrendadorDTO.getPropiedades());
    }
}
