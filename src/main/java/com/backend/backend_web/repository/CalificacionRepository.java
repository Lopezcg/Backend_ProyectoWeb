package com.backend.backend_web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.backend_web.entity.Calificacion;

public interface CalificacionRepository extends CrudRepository<Calificacion, Long> {
    List<Calificacion> findBySolicitudArriendo_Id(Long id);

    @Query("SELECT c FROM Calificacion c WHERE c.solicitudArriendo.propiedad.id = :propiedadId AND c.status = 0")
    List<Calificacion> findByPropiedadId(@Param("propiedadId") Long propiedadId);

}
