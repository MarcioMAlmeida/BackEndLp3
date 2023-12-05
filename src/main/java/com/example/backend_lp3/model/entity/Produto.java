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

    public double calcularDesconto() {

        double percentualPromocao = 0.0;
        double percentualQuantidade = quantidade / quantidadeMax;

        if(percentualQuantidade >= 0 && percentualQuantidade <= 0.1){
            percentualPromocao = 0.0;
        } else if (percentualQuantidade <= 0.2) {
            percentualPromocao = 0.6;
        }else if (percentualQuantidade <= 0.3) {
            percentualPromocao = 0.9;
        }else if (percentualQuantidade <= 0.4) {
            percentualPromocao = 0.12;
        }else if (percentualQuantidade <= 0.5) {
            percentualPromocao = 0.15;
        }else if (percentualQuantidade <= 0.6) {
            percentualPromocao = 0.18;
        }else if (percentualQuantidade <= 0.7) {
            percentualPromocao = 0.21;
        }else if (percentualQuantidade <= 0.8) {
            percentualPromocao = 0.24;
        }else if (percentualQuantidade <= 0.9) {
            percentualPromocao = 0.27;
        }else if (percentualQuantidade == 1) {
            percentualPromocao = 0.30;
        }

        // Calcula o valor do desconto
        double desconto = percentualPromocao * precoUnitario;

        return desconto;
    }

    public double calcularDescontoPorGenereo() {
            double percentualPromocao = 0.0;

            if(genero.getNomeGenero().equals("Masculino")){
                percentualPromocao = 0.1;
            } else if (genero.getNomeGenero().equals("Feminino")) {
                percentualPromocao = 0.15;
            }else if (genero.getNomeGenero().equals("Unissex")) {
                percentualPromocao = 0.05;
            }

            // Calcula o valor do desconto
            double desconto = percentualPromocao * precoUnitario;

            return desconto;
    }

    @OneToOne
    private Departamento departamento;
    @ManyToOne
    private Cor cor;
    @ManyToOne
    private Tamanho tamanho;
    @ManyToOne
    private Genero genero;
}
