package com.backend.backend_web.dto;


import com.backend.backend_web.entity.SolicitudArriendo;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
   
    private Long id;
    private long valor;
    private String banco;
    private long numCuenta;
     

}
