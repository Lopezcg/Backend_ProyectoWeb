package com.backend.backend_web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


import org.hibernate.annotations.Where;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0")
@SQLDelete(sql = "UPDATE arrendador SET  status = 1 WHERE id=?")
public class Arrendador {
    // Atributos y métodos específicos de Arrendador
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String contrasena;
    @OneToMany(mappedBy = "arrendador", cascade = CascadeType.ALL, orphanRemoval = true) // Configura la relación
    private List<Propiedad> propiedades;
    private Integer status = 0; // Valor predeterminado para el atributo status.

}
