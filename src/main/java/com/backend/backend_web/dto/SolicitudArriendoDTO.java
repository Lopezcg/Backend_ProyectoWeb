package com.backend.backend_web.dto;

import java.util.UUID;

import com.backend.backend_web.entity.Arrendatario;
import com.backend.backend_web.entity.Propiedad;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudArriendoDTO {
    private UUID id;
    private String fechainicio;
    private String fechafin;
    private int cantidadPersonas;
}
