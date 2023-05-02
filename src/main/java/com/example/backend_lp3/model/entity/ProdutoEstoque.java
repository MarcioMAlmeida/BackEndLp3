package com.example.backend_lp3.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidade;
    private float precoUnitario;


    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Cor cor;
    @ManyToOne
    private Tamanho tamanho;
    @ManyToOne
    private Genero genero;
}
