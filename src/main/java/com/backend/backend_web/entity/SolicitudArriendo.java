package com.backend.backend_web.entity;
import java.time.LocalDate;


import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.cglib.core.Local;

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
@SQLDelete(sql = "UPDATE solicitud_arriendo SET  status = 1 WHERE id=?")
public class SolicitudArriendo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private LocalDate fechainicio;
    private LocalDate fechafin;
    private int cantidadPersonas;
    private boolean estado;
    @ManyToOne // Establece una relación de muchos a uno
    @JoinColumn(name = "propiedad", referencedColumnName = "id") // Define la columna de unión
    private Propiedad propiedad; // Relación con Arrendador
    @ManyToOne // Establece una relación de muchos a uno
    @JoinColumn(name = "arrendatario", referencedColumnName = "id") // Define la columna de unión
    private Arrendatario arrendatario; //
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago pago;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calificacion_id", referencedColumnName = "id")
    private Calificacion calificacion;
    private Integer status = 0; 

}
