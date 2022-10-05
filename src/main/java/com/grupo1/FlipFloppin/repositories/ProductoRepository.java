package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
