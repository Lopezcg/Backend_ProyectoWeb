package com.backend.backend_web.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("Arrendador")
public class Arrendador extends Usuario {
    // Atributos y métodos específicos de Arrendador
    @OneToMany(mappedBy = "arrendador", cascade = CascadeType.ALL, orphanRemoval = true) // Configura la relación inversa
    private List<Propiedad> propiedades;
}
