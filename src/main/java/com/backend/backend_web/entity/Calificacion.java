package com.backend.backend_web.entity;


import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE calificacion SET  status = 1 WHERE id=?")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
