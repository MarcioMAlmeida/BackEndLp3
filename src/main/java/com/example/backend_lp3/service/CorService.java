package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Cliente;
import com.example.backend_lp3.model.entity.Cor;
import com.example.backend_lp3.model.repository.ClienteRepository;
import com.example.backend_lp3.model.repository.CorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CorService {

    private CorRepository repository;

    public CorService(CorRepository repository) {
        this.repository = repository;
    }

    public List<Cor> getCores(){
        return  repository.findAll();
    }

    public Optional<Cor> getCorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cor salvar(Cor cor) {
        validar(cor);
        return repository.save(cor);
    }

    @Transactional
    public void excluir(Cor cor) {
        Objects.requireNonNull(cor.getId());
        repository.delete(cor);
    }

    public void validar(Cor cor) {
        if (cor.getNomeCor() == null || cor.getNomeCor().trim().equals("")) {
            throw new RegraNegocioException("Cor inválida!");
        }
    }
}
