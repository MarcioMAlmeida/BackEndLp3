package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Fornecedor;
import com.example.backend_lp3.model.repository.FornecedorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    private FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<Fornecedor> getFornecedores(){
        return  repository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) {
        validar(fornecedor);
        return repository.save(fornecedor);
    }

    @Transactional
    public void excluir(Fornecedor fornecedor) {
        Objects.requireNonNull(fornecedor.getId());
        repository.delete(fornecedor);
    }

    public void validar(Fornecedor fornecedor) {
        if (fornecedor.getNome() == null || fornecedor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido!");
        }
        if (fornecedor.getTelefone() == null || fornecedor.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido!");
        }
        if (fornecedor.getEmail() == null || fornecedor.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido!");
        }
        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().trim().equals("")) {
            throw new RegraNegocioException("Cnpj inválido!");
        }
//        if (repository.existsByCnpj(fornecedor.getCnpj()) || repository.existsByEmail(fornecedor.getEmail())) {
//            throw new RegraNegocioException("Fornecedor já cadastrado!");
//        }
    }
}
