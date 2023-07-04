package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.ProdutoPedido;
import com.example.backend_lp3.model.repository.PedidoRepository;
import com.example.backend_lp3.model.repository.ProdutoPedidoRepository;
import com.example.backend_lp3.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoPedidoService {

    private ProdutoPedidoRepository repository;
    private ProdutoRepository produtoRepository;
    private PedidoRepository pedidoRepository;

    @Autowired
    public ProdutoPedidoService(ProdutoPedidoRepository repository, ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<ProdutoPedido> getProdutoPedidos(){
        return  repository.findAll();
    }

    public Optional<ProdutoPedido> getProdutoPedidoById(Long id) {
        return repository.findById(id);
    }

    public List<ProdutoPedido> getProdutosPedidoByPedidoId(Long idPedido) {
        return repository.findByPedidoId(idPedido);
    }

    @Transactional
    public ProdutoPedido salvar(ProdutoPedido produtoPedido) {
        if(!pedidoRepository.existsById(produtoPedido.getPedido().getId())) {
            throw new RegraNegocioException("Pedido não existe");
        }
        if (produtoPedido.getQuantidade() < 0 || produtoPedido.getQuantidade() == null) {
            throw new RegraNegocioException("Valor inválido para quantidade");
        }
        if(produtoPedido.getProduto().getQuantidadeMax() < (produtoPedido.getQuantidade() + produtoPedido.getProduto().getQuantidade())) {
            throw new RegraNegocioException("Quantidade de "+ produtoPedido.getProduto().getNome() +" excede o limite do estoque!");
        }
        if(produtoPedido.getProduto().getQuantidadeMin() > produtoPedido.getQuantidade()) {
            throw new RegraNegocioException("Quantidade de "+ produtoPedido.getProduto().getNome() +" insuficiente!");
        }
        produtoRepository.adicionarUnidade(produtoPedido.getProduto().getId(), produtoPedido.getQuantidade());
        return repository.save(produtoPedido);
    }

    @Transactional
    public void excluir(ProdutoPedido produtoPedido) {
        Objects.requireNonNull(produtoPedido.getId());
        repository.delete(produtoPedido);
    }

}
