package com.backend.backend_web.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.backend.backend_web.entity.Pago;

public interface PagoRepository extends CrudRepository<Pago,UUID> {

}
