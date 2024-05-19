package com.backend.backend_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.backend_web.entity.SolicitudArriendo;

public interface SolicitudArrendamientoRepository extends CrudRepository<SolicitudArriendo, Long> {
    @Query("SELECT c FROM SolicitudArriendo c WHERE c.propiedad.id = :propiedadId AND c.status = 0")
    List<SolicitudArriendo> findByPropiedadId(@Param("propiedadId") Long propiedadId);

    @Query("SELECT c FROM SolicitudArriendo c WHERE c.arrendatario.id = :arrendatarioId AND c.status = 0")
    List<SolicitudArriendo> findByArrendatarioId(@Param("arrendatarioId") Long arrendatarioId);
}
