package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
