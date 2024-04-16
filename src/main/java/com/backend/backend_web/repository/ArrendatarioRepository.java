package com.backend.backend_web.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.backend.backend_web.entity.Arrendatario;

public interface ArrendatarioRepository extends CrudRepository<Arrendatario, Long> {
    @Query("SELECT a FROM Arrendatario a WHERE a.correo = :correo AND a.contrasena = :contrasena")
    Optional<Arrendatario> findByCorreoAndContrasena(@Param("correo") String correo, @Param("contrasena") String contrasena);
}
