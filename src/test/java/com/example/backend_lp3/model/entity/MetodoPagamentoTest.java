package com.example.backend_lp3.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetodoPagamentoTest {

    private MetodoPagamento metodoPagamento;

    @BeforeEach
    public void setUp() {
        metodoPagamento = new MetodoPagamento();
    }


    @Test
    public void testGetSetId() {
        Long id = 2L;
        metodoPagamento.setId(id);
        assertEquals(id, metodoPagamento.getId());
    }

    @Test
    public void testGetSetNomeMetodoPagamento() {
        String nomeMetodoPagamento = "Dinheiro";
        metodoPagamento.setNomeMetodoPagamento(nomeMetodoPagamento);
        assertEquals(nomeMetodoPagamento, metodoPagamento.getNomeMetodoPagamento());
    }

    @Test
    public void testGetSetParcelas() {
        int parcelas = 6;
        metodoPagamento.setParcelas(parcelas);
        assertEquals(parcelas, metodoPagamento.getParcelas());
    }

    @Test
    public void testCalcularJurosDinheiro() {
        metodoPagamento.setNomeMetodoPagamento("Dinheiro");
        metodoPagamento.setParcelas(1);

        assertEquals(0.0, metodoPagamento.calcularJuros(),0.0001);
    }

    @Test
    public void testCalcularJurosCartaoCredito() {
        metodoPagamento.setNomeMetodoPagamento("Cartão de Crédito");
        metodoPagamento.setParcelas(12);

        assertEquals(0.6, metodoPagamento.calcularJuros(),0.0001);
    }

    @Test
    public void testCalcularJurosCartaoDebito() {
        metodoPagamento.setNomeMetodoPagamento("Cartão de Débito");
        metodoPagamento.setParcelas(6);

        assertEquals(0.18, metodoPagamento.calcularJuros(),0.0001);
    }

}