package com.backend.backend_web.dto;

import java.util.List;

import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.entity.SolicitudArriendo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArrendatarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private List<SolicitudArriendo> solicitudes;

    // Create conversion method from Arrendatario to ArrendatarioDTO
    public static ArrendatarioDTO from(Arrendatario arrendatario) {
        ArrendatarioDTO arrendatarioDTO = new ArrendatarioDTO();
        arrendatarioDTO.setId(arrendatario.getId());
        arrendatarioDTO.setNombre(arrendatario.getNombre());
        arrendatarioDTO.setApellido(arrendatario.getApellido());
        arrendatarioDTO.setCorreo(arrendatario.getCorreo());
        arrendatarioDTO.setTelefono(arrendatario.getTelefono());
        arrendatarioDTO.setSolicitudes(arrendatario.getSolicitudes());
        return arrendatarioDTO;
    }

}
