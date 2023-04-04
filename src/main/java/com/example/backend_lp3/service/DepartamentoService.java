package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Departamento;
import com.example.backend_lp3.model.repository.DepartamentoRepository;
import com.example.backend_lp3.model.repository.DepartamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartamentoService {

    private DepartamentoRepository repository;

    public DepartamentoService(DepartamentoRepository repository) {
        this.repository = repository;
    }

    public List<Departamento> getDepartamentos(){
        return  repository.findAll();
    }

    public Optional<Departamento> getDepartamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Departamento salvar(Departamento departamento) {
        validar(departamento);
        return repository.save(departamento);
    }

    @Transactional
    public void excluir(Departamento departamento) {
        Objects.requireNonNull(departamento.getId());
        repository.delete(departamento);
    }

    public void validar(Departamento departamento) {
        if (departamento.getNome() == null || departamento.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
    }
}