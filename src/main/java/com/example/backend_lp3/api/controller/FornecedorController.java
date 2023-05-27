package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.FornecedorDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Endereco;
import com.example.backend_lp3.model.entity.Fornecedor;
import com.example.backend_lp3.service.EnderecoService;
import com.example.backend_lp3.service.FornecedorService;
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
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
@CrossOrigin
public class FornecedorController {

    private final FornecedorService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os Fornecedors")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedors encontrados"),
            @ApiResponse(code = 404, message = "Fornecedors não encontrados")
    })
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um fornecedor específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo fornecedor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity post(@RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedor = converter(dto);
            Endereco endereco = enderecoService.salvar(fornecedor.getEndereco());
            fornecedor.setEndereco(endereco);
            fornecedor = service.salvar(fornecedor);
            return new ResponseEntity(fornecedor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de um fornecedor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody FornecedorDTO dto) {
        if (!service.getFornecedorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor.setId(id);
            Endereco endereco = enderecoService.salvar(fornecedor.getEndereco());
            fornecedor.setEndereco(endereco);
            service.salvar(fornecedor);
            return ResponseEntity.ok(fornecedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um fornecedor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Fornecedor excluído"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if(!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        fornecedor.setEndereco(endereco);
        return fornecedor;
    }
}