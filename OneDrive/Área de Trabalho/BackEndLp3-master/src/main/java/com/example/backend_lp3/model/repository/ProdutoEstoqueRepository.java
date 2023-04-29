package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque, Long> {
}
