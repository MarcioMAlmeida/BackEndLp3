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
    @Query("UPDATE produto P set P.quantidade = P.quantidade - :quantidade where P.id = :idProduto")
    default void retirarUnidade(@Param("idProduto") Long idProduto, @Param("quantidade") Integer quantidade) {
    }

    @Modifying
    @Query("UPDATE produto P set P.quantidade = P.quantidade + :quantidade where P.id = :idProduto")
    default void adicionarUnidade(@Param("idProduto") Long idProduto, @Param("quantidade") Integer quantidade) {
    }
}
