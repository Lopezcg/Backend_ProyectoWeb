package com.backend.backend_web.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import com.backend.backend_web.entity.SolicitudArriendo;

public interface SolicitudArrendamientoRepository extends CrudRepository<SolicitudArriendo,UUID>{

}
