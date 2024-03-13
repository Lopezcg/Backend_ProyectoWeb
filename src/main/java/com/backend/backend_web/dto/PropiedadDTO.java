package com.backend.backend_web.dto;

import java.util.List;


import com.backend.backend_web.entity.Arrendador;
import com.backend.backend_web.entity.SolicitudArriendo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long valor;
    private String estado;
     private Arrendador arrendador;
    private List<SolicitudArriendo> solicitudes;

}
