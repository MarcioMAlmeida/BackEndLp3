package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.FuncionarioDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Endereco;
import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.service.EnderecoService;
import com.example.backend_lp3.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@CrossOrigin
public class FuncionarioController {

    private final FuncionarioService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os funcionários")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionários encontrados"),
            @ApiResponse(code = 404, message = "Funcionários não encontrados")
    })
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um funcionário específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo funcionário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
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

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de um funcionário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody FuncionarioDTO dto) {
        if (!service.getFuncionarioById(id).isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Funcionario funcionario = converter(dto);
            funcionario.setId(id);
            Endereco endereco = enderecoService.salvar(funcionario.getEndereco());
            funcionario.setEndereco(endereco);
            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um funcionário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Funcionário excluído"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if(!funcionario.isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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
