package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.ProductoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoCompraRepository extends JpaRepository<ProductoCompra,Long> {
}
