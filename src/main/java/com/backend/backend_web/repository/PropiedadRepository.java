package com.backend.backend_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.backend_web.entity.Propiedad;

public interface PropiedadRepository extends CrudRepository<Propiedad, Long> {
    @Query("SELECT c FROM Propiedad c WHERE c.arrendador.id = :arrendadorId AND c.status = 0")
    List<Propiedad> findByArrendadorId(@Param("arrendadorId") Long arrendadorId);
}
