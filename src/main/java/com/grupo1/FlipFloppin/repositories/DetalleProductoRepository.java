package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.DetalleProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto,Long> {

}
