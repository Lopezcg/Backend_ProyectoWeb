package com.backend.backend_web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.backend.backend_web.entity.Calificacion;

public interface CalificacionRepository extends CrudRepository<Calificacion, Long> {
    List<Calificacion> findBySolicitudArriendo_Id(Long id);

}
