package com.backend.backend_web.entity;

import java.util.List;


import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@SQLDelete(sql = "UPDATE propiedad SET  status = 1 WHERE id=?")

public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Long valor;
    private String estado;
    @ManyToOne(fetch = FetchType.LAZY) // Establece una relación de muchos a uno
    @JoinColumn(name = "arrendador", referencedColumnName = "id") // Define la columna de unión
    private Arrendador arrendador; // Relación con Arrendador
    private Integer status = 0; // Valor predeterminado para el atributo status.
    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true) // Configura la relación
                                                                                           
    private List<SolicitudArriendo> solicitudes;

}
