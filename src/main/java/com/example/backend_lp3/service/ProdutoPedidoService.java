package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.ProdutoPedido;
import com.example.backend_lp3.model.repository.PedidoRepository;
import com.example.backend_lp3.model.repository.ProdutoEstoqueRepository;
import com.example.backend_lp3.model.repository.ProdutoPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoPedidoService {

    private ProdutoPedidoRepository repository;
    private ProdutoEstoqueRepository produtoEstoqueRepository;
    private PedidoRepository pedidoRepository;

    public ProdutoPedidoService(ProdutoPedidoRepository repository, ProdutoEstoqueRepository produtoEstoqueRepository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.produtoEstoqueRepository = produtoEstoqueRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<ProdutoPedido> getProdutoPedidos(){
        return  repository.findAll();
    }

    public Optional<ProdutoPedido> getProdutoPedidoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ProdutoPedido salvar(ProdutoPedido produtoPedido) {

        if(!pedidoRepository.existsById(produtoPedido.getPedido().getId())) {
            throw new RegraNegocioException("Pedido com ID inválido");
        }
        if (produtoPedido.getQuantidade() < 0 || produtoPedido.getQuantidade() == null) {
            throw new RegraNegocioException("Valor inválido para quantidade");
        }
        if(produtoPedido.getProdutoEstoque().getProduto().getQuantidadeMax() < produtoPedido.getQuantidade()) {
            throw new RegraNegocioException("Quantidade de "+ produtoPedido.getProdutoEstoque().getProduto().getNomeProduto() +" excede o limite do estoque!");
        }
        if(produtoPedido.getProdutoEstoque().getProduto().getQuantidadeMin() > produtoPedido.getQuantidade()) {
            throw new RegraNegocioException("Quantidade de "+ produtoPedido.getProdutoEstoque().getProduto().getNomeProduto() +" insuficiente!");
        }

        produtoEstoqueRepository.adicionarUnidadeEstoque(produtoPedido.getProdutoEstoque().getId(), produtoPedido.getQuantidade());

        return repository.save(produtoPedido);
    }

    @Transactional
    public void excluir(ProdutoPedido produtoPedido) {
        Objects.requireNonNull(produtoPedido.getId());
        repository.delete(produtoPedido);
    }

}
