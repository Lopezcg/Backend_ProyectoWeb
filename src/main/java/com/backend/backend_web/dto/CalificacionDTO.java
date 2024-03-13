package com.backend.backend_web.dto;



import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private float puntuacion;
    private Integer arrendatarioId;
    private Integer arrendadorId;
    private Integer solicitudId;
}
