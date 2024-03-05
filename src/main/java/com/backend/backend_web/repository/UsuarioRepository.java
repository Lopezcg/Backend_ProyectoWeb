package com.backend.backend_web.repository;

import org.springframework.data.repository.CrudRepository;
import com.backend.backend_web.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {

}
