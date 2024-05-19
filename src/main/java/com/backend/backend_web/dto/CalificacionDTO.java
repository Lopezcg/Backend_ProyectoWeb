package com.backend.backend_web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionDTO {
    private Long id;
    private String comentario;
    private Float puntuacion;
    private SolicitudArriendoDTO solicitudArriendo;
}
