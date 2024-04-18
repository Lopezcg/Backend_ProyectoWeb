package com.backend.backend_web.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.backend_web.entity.Arrendador;

public interface ArrendadorRepository extends CrudRepository<Arrendador, Long> {
    //Find by correo y contrasena
    @Query("SELECT a FROM Arrendador a WHERE a.correo = :correo AND a.contrasena = :contrasena")
    Optional<Arrendador> findByCorreoAndContrasena(@Param("correo") String correo, @Param("contrasena") String contrasena);

}
