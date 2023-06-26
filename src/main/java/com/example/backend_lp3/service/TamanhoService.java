package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Tamanho;
import com.example.backend_lp3.model.repository.TamanhoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TamanhoService {

    private TamanhoRepository repository;

    public TamanhoService(TamanhoRepository repository) {
        this.repository = repository;
    }

    public List<Tamanho> getTamanhos(){
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
        if (tamanho.getNomeTamanho() == null || tamanho.getNomeTamanho().trim().equals("")) {
            throw new RegraNegocioException("Tamanho inválido!");
        }
//        if (repository.existsByNomeTamanho(tamanho.getNomeTamanho()) ) {
//            throw new RegraNegocioException("Tamanho já cadastrado!");
//        }
    }
}
