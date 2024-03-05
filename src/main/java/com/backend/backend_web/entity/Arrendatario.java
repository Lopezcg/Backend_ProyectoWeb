package com.backend.backend_web.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("Arrendatario")
public class Arrendatario extends Usuario {
    // Atributos y métodos específicos de Arrendatario
    @OneToMany(mappedBy = "arrendatario", cascade = CascadeType.ALL, orphanRemoval = true) // Configura la relación inversa
    private List<SolicitudArriendo> solicitudes;
}
