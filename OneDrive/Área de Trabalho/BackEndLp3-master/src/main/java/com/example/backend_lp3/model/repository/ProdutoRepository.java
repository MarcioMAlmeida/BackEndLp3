package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
