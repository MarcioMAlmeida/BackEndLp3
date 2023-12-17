package com.example.backend_lp3.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String nomeMetodoPagamento;
    private int parcelas;

    public double calcularJuros() {
        double taxaJuros = 0.0;

        if(nomeMetodoPagamento.equals("Dinheiro")) {
            taxaJuros = 0.0;
        } else if (nomeMetodoPagamento.equals("Cartão de Crédito")) {
            taxaJuros = 0.05;
        } else if (nomeMetodoPagamento.equals("Cartão de Débito")) {
            taxaJuros = 0.03;
        }

        return taxaJuros * parcelas;
    }
}
