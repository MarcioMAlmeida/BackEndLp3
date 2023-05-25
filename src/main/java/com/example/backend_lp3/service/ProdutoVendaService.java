package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.ProdutoVenda;
import com.example.backend_lp3.model.repository.ProdutoEstoqueRepository;
import com.example.backend_lp3.model.repository.ProdutoVendaRepository;
import com.example.backend_lp3.model.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoVendaService {

    private ProdutoVendaRepository repository;
    private VendaRepository vendaRepository;
    private ProdutoEstoqueRepository produtoEstoqueRepository;

    public ProdutoVendaService(ProdutoVendaRepository repository, VendaRepository vendaRepository, ProdutoEstoqueRepository produtoEstoqueRepository) {
        this.repository = repository;
        this.vendaRepository = vendaRepository;
        this.produtoEstoqueRepository = produtoEstoqueRepository;
    }

    public List<ProdutoVenda> getProdutoVendas(){
        return  repository.findAll();
    }

    public Optional<ProdutoVenda> getProdutoVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ProdutoVenda salvar(ProdutoVenda produtoVenda) {
        if(!vendaRepository.existsById(produtoVenda.getVenda().getId())) {
            throw new RegraNegocioException("Venda com ID inválido");
        }
        if (produtoVenda.getQuantidade() < 0 || produtoVenda.getQuantidade() == null) {
            throw new RegraNegocioException("Valor inválido para quantidade");
        }
        Integer unidadesDisponiveis = produtoVenda.getProdutoEstoque().getQuantidade() - produtoVenda.getQuantidade();
        if(unidadesDisponiveis < 0) {
            throw new RegraNegocioException("Quantidade de "+ produtoVenda.getProdutoEstoque().getProduto().getNomeProduto() +" insuficiente!");
        }

        produtoEstoqueRepository.retirarUnidadeEstoque(produtoVenda.getProdutoEstoque().getId(), produtoVenda.getQuantidade());
        vendaRepository.atualizarPreco(produtoVenda.getVenda().getId(), (produtoVenda.getProdutoEstoque().getPrecoUnitario() * produtoVenda.getQuantidade()));

        return repository.save(produtoVenda);
    }

    @Transactional
    public void excluir(ProdutoVenda produtoVenda) {
        Objects.requireNonNull(produtoVenda.getId());
        repository.delete(produtoVenda);
    }

}
