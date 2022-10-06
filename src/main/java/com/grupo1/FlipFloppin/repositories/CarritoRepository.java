package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito,Long> {
}
