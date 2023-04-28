package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.ProdutoEstoque;
import com.example.backend_lp3.model.repository.ProdutoEstoqueRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoEstoqueService {

    private ProdutoEstoqueRepository repository;

    public ProdutoEstoqueService(ProdutoEstoqueRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoEstoque> getProdutosEstoque(){
        return  repository.findAll();
    }

    public Optional<ProdutoEstoque> getProdutoEstoqueById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ProdutoEstoque salvar(ProdutoEstoque produtoEstoque) {
        validar(produtoEstoque);
        return repository.save(produtoEstoque);
    }

    @Transactional
    public void excluir(ProdutoEstoque produtoEstoque) {
        Objects.requireNonNull(produtoEstoque.getId());
        repository.delete(produtoEstoque);
    }

    public void validar(ProdutoEstoque produtoEstoque) {
        //if (produtoEstoque.getNome() == null || produtoEstoque.getNome().trim().equals("")) {
        //    throw new RegraNegocioException("Nome inválido!");
        //}
    }
}
