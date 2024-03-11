package com.backend.backend_web.dto;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private Long valor;
    private String estado;
    private ArrendadorDTO arrendador;
    private Integer status = 0;

}
