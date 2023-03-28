package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Tamanho;
import com.example.backend_lp3.model.repository.TamanhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TamanhoService {

    private TamanhoRepository repository;

    public TamanhoService(TamanhoRepository repository) {
        this.repository = repository;
    }

    public List<Tamanho> getTamanho(){
        return  repository.findAll();
    }

    public Optional<Tamanho> getTamanhoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Tamanho salvar(Tamanho tamanho) {
        validar(tamanho);
        return repository.save(tamanho);
    }

    @Transactional
    public void excluir(Tamanho tamanho) {
        Objects.requireNonNull(tamanho.getId());
        repository.delete(tamanho);
    }

    public void validar(Tamanho tamanho) {
        if (tamanho.getNome() == null || tamanho.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
    }
}
