package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Genero;
import com.example.backend_lp3.model.repository.GeneroRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GeneroService {

    private GeneroRepository repository;

    public GeneroService(GeneroRepository repository) {
        this.repository = repository;
    }

    public List<Genero> getGeneros(){
        return  repository.findAll();
    }

    public Optional<Genero> getGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Genero salvar(Genero genero) {
        validar(genero);
        return repository.save(genero);
    }

    @Transactional
    public void excluir(Genero genero) {
        Objects.requireNonNull(genero.getId());
        repository.delete(genero);
    }

    public void validar(Genero genero) {
        if (genero.getNomeGenero() == null || genero.getNomeGenero().trim().equals("")) {
            throw new RegraNegocioException("Gênero inválido!");
        }
    }
}
