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
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataVenda;
    private float precoTotal;

    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private ProdutoEstoque produtoEstoque;
    @ManyToOne
    private MetodoPagamento metodoPagamento;
}
