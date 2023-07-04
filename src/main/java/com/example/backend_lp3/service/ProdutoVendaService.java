package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Produto;
import com.example.backend_lp3.model.entity.ProdutoVenda;
import com.example.backend_lp3.model.repository.ProdutoRepository;
import com.example.backend_lp3.model.repository.ProdutoVendaRepository;
import com.example.backend_lp3.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoVendaService {

    private ProdutoVendaRepository repository;
    private VendaRepository vendaRepository;
    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoVendaService(ProdutoVendaRepository repository, VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoVenda> getProdutoVendas(){
        return  repository.findAll();
    }

    public Optional<ProdutoVenda> getProdutoVendaById(Long id) {
        return repository.findById(id);
    }

    public List<ProdutoVenda> getProdutosVendaByVendaId(Long idVenda) {
        return repository.findByVendaId(idVenda);
    }

    @Transactional
    public ProdutoVenda salvar(ProdutoVenda produtoVenda) {
        if(!vendaRepository.existsById(produtoVenda.getVenda().getId())) {
            throw new RegraNegocioException("Venda não existe");
        }
        if (produtoVenda.getQuantidade() < 0 || produtoVenda.getQuantidade() == null) {
            throw new RegraNegocioException("Valor inválido para quantidade");
        }
        Integer unidadesDisponiveis = produtoVenda.getProduto().getQuantidade() - produtoVenda.getQuantidade();
        if(unidadesDisponiveis < 0) {
            throw new RegraNegocioException("Quantidade de "+ produtoVenda.getProduto().getNome() +" insuficiente!");
        }
        Produto produto = produtoVenda.getProduto();
        repository.save(produtoVenda); // Salva o produtoVenda no repositório primeiro

        produtoRepository.retirarUnidade(produto.getId(), produtoVenda.getQuantidade()); // Subtrai a quantidade do produto

        return produtoVenda;
    }

    @Transactional
    public void excluir(ProdutoVenda produtoVenda) {
        Objects.requireNonNull(produtoVenda.getId());
        produtoRepository.adicionarUnidade(produtoVenda.getProduto().getId(), produtoVenda.getQuantidade());
        repository.delete(produtoVenda);
    }

}
