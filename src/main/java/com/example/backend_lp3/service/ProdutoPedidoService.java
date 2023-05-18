package com.example.backend_lp3.service;

import com.example.backend_lp3.model.entity.ProdutoPedido;
import com.example.backend_lp3.model.repository.ProdutoPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoPedidoService {

    private ProdutoPedidoRepository repository;

    public ProdutoPedidoService(ProdutoPedidoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoPedido> getProdutoPedidos(){
        return  repository.findAll();
    }

    public Optional<ProdutoPedido> getProdutoPedidoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ProdutoPedido salvar(ProdutoPedido produtoPedido) {
        validar(produtoPedido);
        return repository.save(produtoPedido);
    }

    @Transactional
    public void excluir(ProdutoPedido produtoPedido) {
        Objects.requireNonNull(produtoPedido.getId());
        repository.delete(produtoPedido);
    }

    public void validar(ProdutoPedido produtoPedido) {
        //if (produtoPedido.getNome() == null || produtoPedido.getNome().trim().equals("")) {
        //  throw new RegraNegocioException("Nome inválido!");
        //}
    }

}
