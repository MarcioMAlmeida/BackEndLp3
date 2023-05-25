package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Modifying
    @Query("UPDATE venda V SET V.preco_total = V.preco_total + :preco WHERE V.id = :idVenda")
    default void atualizarPreco(@Param("idVenda") Long idVenda, @Param("preco") Double preco) {

    }
}
