package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Pedido;
import com.example.backend_lp3.model.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> getPedido(){
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
        //if (pedido.getNome() == null || pedido.getNome().trim().equals("")) {
        //    throw new RegraNegocioException("Nome inválido!");
        //}
    }
}
