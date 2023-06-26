package com.example.backend_lp3.service;

import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Endereco;
import com.example.backend_lp3.model.repository.EnderecoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<Endereco> getEnderecos() {
        return repository.findAll();
    }

    public Optional<Endereco> getEnderecoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Endereco salvar(Endereco endereco) {
        validar(endereco);
        return repository.save(endereco);
    }

    public void validar (Endereco endereco) {
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().equals("")) {
            throw new RegraNegocioException("Logradouro inválido!");
        }
        if (endereco.getNumero() == null ) {
            throw new RegraNegocioException("Número inválido!");
        }
        if (endereco.getComplemento() == null || endereco.getComplemento().trim().equals("")) {
            throw new RegraNegocioException("Complemento inválido!");
        }
        if (endereco.getBairro() == null || endereco.getBairro().trim().equals("")) {
            throw new RegraNegocioException("Bairro inválido!");
        }
        if (endereco.getCidade() == null || endereco.getCidade().trim().equals("")) {
            throw new RegraNegocioException("Cidade inválido!");
        }
        if (endereco.getUf() == null || endereco.getUf().trim().equals("")) {
            throw new RegraNegocioException("Estado inválido!");
        }
        if (endereco.getCep() == null || endereco.getCep().trim().equals("")) {
            throw new RegraNegocioException("CEP inválido!");
        }
    }
}
