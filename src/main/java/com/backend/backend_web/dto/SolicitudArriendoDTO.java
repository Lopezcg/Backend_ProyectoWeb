package com.backend.backend_web.dto;

import java.time.LocalDate;


import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.entity.Calificacion;
import com.backend.backend_web.entity.Pago;
import com.backend.backend_web.entity.Propiedad;
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
    private int cantidadPersonas;
    private Propiedad propiedad;
    private Arrendatario arrendatario;
    private Pago pago;
     private Calificacion calificacion;
}
