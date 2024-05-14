package com.backend.backend_web.dto;

import com.backend.backend_web.entity.SolicitudArriendo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;
    private Long valor;
    private String banco;
    private Long numCuenta;

}
