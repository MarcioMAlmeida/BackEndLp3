package com.example.backend_lp3.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProdutoTest {

    private Produto produto;
    private Genero generoMock;

    @BeforeEach
    public void setUp() {
        produto = new Produto();
        produto.setQuantidadeMax(100);
        produto.setPrecoUnitario(100.0);
        generoMock = mock(Genero.class);
        produto.setGenero(generoMock);
    }

    @Test
    public void testaPercentual0() {
        produto.setQuantidade(0);
        assertEquals(0.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA10() {
        produto.setQuantidade(10);
        assertEquals(0.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA20() {
        produto.setQuantidade(20);
        assertEquals(6.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA30() {
        produto.setQuantidade(30);
        assertEquals(9.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA40() {
        produto.setQuantidade(40);
        assertEquals(12.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA50() {
        produto.setQuantidade(50);
        assertEquals(15.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA60() {
        produto.setQuantidade(60);
        assertEquals(18.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA70() {
        produto.setQuantidade(70);
        assertEquals(21.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA80() {
        produto.setQuantidade(80);
        assertEquals(24.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA90() {
        produto.setQuantidade(90);
        assertEquals(27.0, produto.calcularDesconto());
    }

    @Test
    public void testaPercentualInferiorA100() {
        produto.setQuantidade(100);
        assertEquals(30.0, produto.calcularDesconto());
    }


    @Test
    public void testDescontoGeneroMasculino() {
        when(generoMock.getNomeGenero()).thenReturn("Masculino");
        assertEquals(10.0, produto.calcularDescontoPorGenero());
    }

    @Test
    public void testDescontoGeneroFeminino() {
        when(generoMock.getNomeGenero()).thenReturn("Feminino");
        assertEquals(15.0, produto.calcularDescontoPorGenero());
    }

    @Test
    public void testDescontoGeneroUnissex() {
        when(generoMock.getNomeGenero()).thenReturn("Unissex");
        assertEquals(5.0, produto.calcularDescontoPorGenero());
    }

    @Test
    public void testDescontoGeneroDesconhecido() {
        when(generoMock.getNomeGenero()).thenReturn("Desconhecido");
        assertEquals(0.0, produto.calcularDescontoPorGenero());
    }

    @Test
    public void testId() {
        Long id = 1L;
        produto.setId(id);
        assertEquals(id, produto.getId());
    }

    @Test
    public void testNome() {
        String nome = "Produto Teste";
        produto.setNome(nome);
        assertEquals(nome, produto.getNome());
    }

    @Test
    public void testQuantidadeMin() {
        Integer quantidadeMin = 5;
        produto.setQuantidadeMin(quantidadeMin);
        assertEquals(quantidadeMin, produto.getQuantidadeMin());
    }

    @Test
    public void testQuantidadeMax() {
        Integer quantidadeMax = 100;
        produto.setQuantidadeMax(quantidadeMax);
        assertEquals(quantidadeMax, produto.getQuantidadeMax());
    }

    @Test
    public void testQuantidade() {
        Integer quantidade = 10;
        produto.setQuantidade(quantidade);
        assertEquals(quantidade, produto.getQuantidade());
    }

    @Test
    public void testPrecoUnitario() {
        Double precoUnitario = 25.50;
        produto.setPrecoUnitario(precoUnitario);
        assertEquals(precoUnitario, produto.getPrecoUnitario(), 0.001);
    }

    @Test
    public void testDepartamento() {
        Departamento departamento = new Departamento();
        produto.setDepartamento(departamento);
        assertEquals(departamento, produto.getDepartamento());
    }

    @Test
    public void testCor() {
        Cor cor = new Cor();
        produto.setCor(cor);
        assertEquals(cor, produto.getCor());
    }

    @Test
    public void testTamanho() {
        Tamanho tamanho = new Tamanho();
        produto.setTamanho(tamanho);
        assertEquals(tamanho, produto.getTamanho());
    }

    @Test
    public void testGenero() {
        Genero genero = new Genero();
        produto.setGenero(genero);
        assertEquals(genero, produto.getGenero());
    }
}