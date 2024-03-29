package com.backend.backend_web.dto;

import java.util.List;

import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.Propiedad;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArrendadorDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private List<Propiedad> propiedades;

    

}
