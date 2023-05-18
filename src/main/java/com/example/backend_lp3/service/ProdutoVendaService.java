package com.example.backend_lp3.service;

import com.example.backend_lp3.model.entity.ProdutoVenda;
import com.example.backend_lp3.model.repository.ProdutoVendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoVendaService {

    private ProdutoVendaRepository repository;

    public ProdutoVendaService(ProdutoVendaRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoVenda> getProdutoVendas(){
        return  repository.findAll();
    }

    public Optional<ProdutoVenda> getProdutoVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ProdutoVenda salvar(ProdutoVenda produtoVenda) {
        validar(produtoVenda);
        return repository.save(produtoVenda);
    }

    @Transactional
    public void excluir(ProdutoVenda produtoVenda) {
        Objects.requireNonNull(produtoVenda.getId());
        repository.delete(produtoVenda);
    }

    public void validar(ProdutoVenda produtoVenda) {
        //if (produtoVenda.getNome() == null || produtoVenda.getNome().trim().equals("")) {
        //  throw new RegraNegocioException("Nome inválido!");
        //}
    }

}
