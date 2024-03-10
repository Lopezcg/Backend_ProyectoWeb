package com.backend.backend_web.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import com.backend.backend_web.entity.Arrendador;

public interface ArrendadorRepository extends CrudRepository<Arrendador, Long> {

}
