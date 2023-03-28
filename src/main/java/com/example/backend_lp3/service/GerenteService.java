package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Gerente;
import com.example.backend_lp3.model.repository.GerenteRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GerenteService {

    private GerenteRepository repository;

    public GerenteService(GerenteRepository repository) {
        this.repository = repository;
    }

    public List<Gerente> getGerente(){
        return  repository.findAll();
    }

    public Optional<Gerente> getGerenteById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Gerente salvar(Gerente gerente) {
        validar(gerente);
        return repository.save(gerente);
    }

    @Transactional
    public void excluir(Gerente gerente) {
        Objects.requireNonNull(gerente.getId());
        repository.delete(gerente);
    }

    public void validar(Gerente gerente) {
        if (gerente.getNome() == null || gerente.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
    }
}
