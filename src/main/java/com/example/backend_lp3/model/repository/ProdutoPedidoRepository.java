package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {
    List<ProdutoPedido> findByPedidoId(Long idPedido);
}
