package com.backend.backend_web.dto;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CalificacionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String comentario;
    private float puntuacion;
    @Column(name = "arrendatario_id")
    private Integer arrendatarioId;
    @Column(name = "arrendador_id")
    private Integer arrendadorId;
    @Column(name = "solicitud_id")
    private Integer solicitudId;
    private Integer status = 0; // Valor predeterminado para el atributo status.

}
