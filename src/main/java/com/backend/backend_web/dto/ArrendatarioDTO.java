package com.backend.backend_web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArrendatarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    @JsonIgnore
    private List<CalificacionDTO> calificaciones;
}
