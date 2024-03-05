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
@SQLDelete(sql = "UPDATE application SET  status = 1 WHERE id=?")
public class SolicitudArriendo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fechainicio;
    private String fechafin;
    private int cantidadPersonas;
    private String estado;
    @ManyToOne // Establece una relación de muchos a uno
    @JoinColumn(name = "propiedad_id", referencedColumnName = "id") // Define la columna de unión
    private Propiedad arrendador; // Relación con Arrendador
    @Column(name = "arrendador_id")
    private Integer arrendadorId;
    @ManyToOne // Establece una relación de muchos a uno
    @JoinColumn(name = "arrendatario_id", referencedColumnName = "id") // Define la columna de unión
    private Arrendatario arrendatario; // Relación con Arrendador

}
