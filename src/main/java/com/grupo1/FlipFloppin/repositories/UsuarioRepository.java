package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Query("SELECT c FROM Usuario c WHERE c.email =:email")
    Usuario findByEmail(@Param("email")String email);
}
