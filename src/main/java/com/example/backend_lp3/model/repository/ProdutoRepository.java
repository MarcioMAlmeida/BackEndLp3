package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Modifying
    @Query("UPDATE Produto p SET p.quantidade = p.quantidade - :quantidade WHERE p.id = :idProduto")
    void retirarUnidade(@Param("idProduto") Long idProduto, @Param("quantidade") Integer quantidade);

    @Modifying
    @Query("UPDATE Produto p SET p.quantidade = p.quantidade + :quantidade WHERE p.id = :idProduto")
    void adicionarUnidade(@Param("idProduto") Long idProduto, @Param("quantidade") Integer quantidade);
}
