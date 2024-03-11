package com.backend.backend_web.dto;
import java.util.UUID;


import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
   
    private UUID id;
    private long valor;

}
