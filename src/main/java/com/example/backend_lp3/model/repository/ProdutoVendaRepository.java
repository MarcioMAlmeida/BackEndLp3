package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {
    List<ProdutoVenda> findByVendaId(Long idVenda);
}
