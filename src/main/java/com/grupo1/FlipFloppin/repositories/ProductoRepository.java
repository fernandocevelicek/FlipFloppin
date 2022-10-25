package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @Query(value = "SELECT * FROM Producto p WHERE p.nombre LIKE %?1% "+
                    "OR p.marca LIKE %?1%" +
                    "OR p.sexo LIKE %?1%" +
                    "OR p.precio LIKE %?1%", nativeQuery = true)
    List<Producto> findByFilter(String searchFilter);
}
