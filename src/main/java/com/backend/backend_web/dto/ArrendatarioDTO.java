package com.backend.backend_web.dto;

import java.util.UUID;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArrendatarioDTO {
   
    private UUID id;
    private String nombre;
    private String apellido;
    private String correo; 
    
}
