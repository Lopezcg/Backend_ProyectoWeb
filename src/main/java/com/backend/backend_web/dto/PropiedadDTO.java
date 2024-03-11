package com.backend.backend_web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadDTO {
    private long id;
    private String nombre;
    private String descripcion;
    private Long valor;
    private String estado;
    private ArrendadorDTO arrendador;
    private Integer status = 0;

}
