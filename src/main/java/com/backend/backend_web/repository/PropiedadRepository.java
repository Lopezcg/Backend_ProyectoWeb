package com.backend.backend_web.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.backend.backend_web.entity.Propiedad;

public interface PropiedadRepository extends CrudRepository<Propiedad,UUID> {

}
