package com.backend.backend_web.entity;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private Float puntuacion;
    private Integer status = 0; // Valor predeterminado para el atributo status.
    @ManyToOne // Establece una relación de muchos a uno
    @JoinColumn(name = "solicitudArriendo", referencedColumnName = "id") // Define la columna de unión
    private SolicitudArriendo solicitudArriendo; // Relación con SolicitudArriendo
    @ManyToOne
    @JoinColumn(name = "arrendatario", referencedColumnName = "id")
    private Arrendatario arrendatario;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calificacion_id", referencedColumnName = "id")
    private SolicitudArriendo solicitud;

}
