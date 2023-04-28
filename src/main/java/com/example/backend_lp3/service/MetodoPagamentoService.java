package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import com.example.backend_lp3.model.repository.MetodoPagamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MetodoPagamentoService {

    private MetodoPagamentoRepository repository;

    public MetodoPagamentoService(MetodoPagamentoRepository repository) {
        this.repository = repository;
    }

    public List<MetodoPagamento> getMetodosPagamento(){
        return  repository.findAll();
    }

    public Optional<MetodoPagamento> getMetodoPagamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public MetodoPagamento salvar(MetodoPagamento metodoPagamento) {
        validar(metodoPagamento);
        return repository.save(metodoPagamento);
    }

    @Transactional
    public void excluir(MetodoPagamento metodoPagamento) {
        Objects.requireNonNull(metodoPagamento.getId());
        repository.delete(metodoPagamento);
    }

    public void validar(MetodoPagamento metodoPagamento) {
        if (metodoPagamento.getNome() == null || metodoPagamento.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
    }
}
