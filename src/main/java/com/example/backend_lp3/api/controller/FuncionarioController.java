package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.FuncionarioDTO;
import com.example.backend_lp3.api.dto.FuncionarioDTO;
import com.example.backend_lp3.api.dto.FuncionarioDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Endereco;
import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.service.EnderecoService;
import com.example.backend_lp3.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario funcionario = converter(dto);
            Endereco endereco = enderecoService.salvar(funcionario.getEndereco());
            funcionario.setEndereco(endereco);
            funcionario = service.salvar(funcionario);
            return new ResponseEntity(funcionario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        funcionario.setEndereco(endereco);
        return funcionario;
    }

}
