package com.backend.backend_web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudArriendoDTO {
    private Long id;
    private LocalDate fechainicio;
    private LocalDate fechafin;
    private boolean estado;
    private Integer cantidadPersonas;
    private ArrendatarioDTO arrendatario;
    private PropiedadDTO propiedad;

}
