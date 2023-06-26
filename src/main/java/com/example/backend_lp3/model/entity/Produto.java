package com.example.backend_lp3.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer quantidadeMin;
    private Integer quantidadeMax;
    private Integer quantidade = 0;
    private Double precoUnitario;

    @OneToOne
    private Departamento departamento;
    @ManyToOne
    private Cor cor;
    @ManyToOne
    private Tamanho tamanho;
    @ManyToOne
    private Genero genero;
}
