package com.backend.backend_web.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE arrendatario SET  status = 1 WHERE id=?")
public class Arrendatario {
    // Atributos y métodos específicos de Arrendatario
     @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    @OneToMany(mappedBy = "arrendatario", cascade = CascadeType.ALL, orphanRemoval = true) // Configura la relación inversa
    private List<SolicitudArriendo> solicitudes;
    private Integer status = 0; // Valor predeterminado para el atributo status.

}
