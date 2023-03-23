package com.example.backend_lp3.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private float precoTotal;

    @ManyToOne
    private Fornecedor fornecedor;
    @ManyToOne
    private Gerente gerente;
    @ManyToOne
    private ProdutoEstoque produtoEstoque;
}
