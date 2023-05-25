package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque, Long> {

    @Modifying
    @Query("UPDATE produto_estoque PE set PE.quantidade = PE.quantidade - :quantidade where PE.id = :idProdutoEstoque")
    default void retirarUnidadeEstoque(@Param("idProdutoEstoque") Long idProdutoEstoque, @Param("quantidade") Integer quantidade) {

    }

    @Modifying
    @Query("UPDATE produto_estoque PE set PE.quantidade = PE.quantidade + :quantidade where PE.id = :idProdutoEstoque")
    default void adicionarUnidadeEstoque(@Param("idProdutoEstoque") Long idProdutoEstoque, @Param("quantidade") Integer quantidade) {

    }

}
