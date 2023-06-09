package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Pedido;
import com.example.backend_lp3.model.repository.PedidoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> getPedidos(){
        return  repository.findAll();
    }

    public Optional<Pedido> getPedidoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validar(pedido);
        return repository.save(pedido);
    }

    @Transactional
    public void excluir(Pedido pedido) {
        Objects.requireNonNull(pedido.getId());
        repository.delete(pedido);
    }

    public void validar(Pedido pedido) {
        if (pedido.getDataPedido() == null) {
            throw new RegraNegocioException("Data do pedido inválida!");
        }
        if (pedido.getDataEntrega() == null) {
            throw new RegraNegocioException("Data da entrega inválida!");
        }
    }
}
