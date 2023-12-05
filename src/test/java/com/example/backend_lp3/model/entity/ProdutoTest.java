package com.example.backend_lp3.model.entity;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    public void deveCalcularPromocaoDe30PorcentoSeQuantidadeForIgualAQuantidadeMaxima() {
        Produto produto = new Produto();
        produto.setPrecoUnitario(100.0);
        produto.setQuantidade(100);
        produto.setQuantidadeMax(100);

        double desconto = produto.calcularDesconto();

        assertEquals(30.0, desconto);
    }

    @Test
    public void deveCalcularPromocaoDe10PorcentoSeQuantidadeForIgualAQuantidadeMinima() {
        Produto produto = new Produto();
        produto.setPrecoUnitario(100.0);
        produto.setQuantidade(10);
        produto.setQuantidadeMax(100);

        double desconto = produto.calcularDesconto();

        assertEquals(0.0, desconto);
    }

    @Mock
    private Genero genero;

    @Test
    public void deveCalcularDescontoDe10PorcentoParaProdutosMasculinos() {
        Produto produto = new Produto();
        produto.setGenero(genero);
        Mockito.when(genero.getNomeGenero()).thenReturn("Masculino");

        double desconto = produto.calcularDescontoPorGenereo();

        assertEquals(10.0, desconto, 0.001);
    }

    @Test
    public void deveCalcularDescontoDe15PorcentoParaProdutosFemininos() {
        Produto produto = new Produto();
        produto.setGenero(genero);
        Mockito.when(genero.getNomeGenero()).thenReturn("Feminino");

        double desconto = produto.calcularDescontoPorGenereo();

        assertEquals(15.0, desconto, 0.001);
    }

    @Test
    public void deveCalcularDescontoDe5PorcentoParaProdutosUnissex() {
        Produto produto = new Produto();
        produto.setGenero(genero);
        Mockito.when(genero.getNomeGenero()).thenReturn("Unissex");

        double desconto = produto.calcularDescontoPorGenereo();

        assertEquals(5.0, desconto, 0.001);
    }
}