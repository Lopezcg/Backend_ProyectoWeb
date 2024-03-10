package com.backend.backend_web.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import com.backend.backend_web.entity.Calificacion;

public interface CalificacionRepository extends CrudRepository<Calificacion,UUID>{

}
