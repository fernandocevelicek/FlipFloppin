package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository  extends JpaRepository<Pedido,Long> {
}
