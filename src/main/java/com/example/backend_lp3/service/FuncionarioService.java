package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.model.repository.FuncionarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> getFuncionarios(){
        return  repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (funcionario.getTelefone() == null || funcionario.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido!");
        }
        if (funcionario.getEmail() == null || funcionario.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido!");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido!");
        }
    }
}
