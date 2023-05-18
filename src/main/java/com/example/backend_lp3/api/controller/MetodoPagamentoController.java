package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.MetodoPagamentoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import com.example.backend_lp3.service.MetodoPagamentoService;
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
@RequestMapping("/api/v1/metodos-pagamento")
@RequiredArgsConstructor
public class MetodoPagamentoController {

    private final MetodoPagamentoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os métodos de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Métodos de pagamento encontrados"),
            @ApiResponse(code = 404, message = "Métodos de pagamento não encontrados")
    })
    public ResponseEntity get() {
        List<MetodoPagamento> metodosPagamento = service.getMetodosPagamento();
        return ResponseEntity.ok(metodosPagamento.stream().map(MetodoPagamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um método de pagamento específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Método de Pagamento encontrado"),
            @ApiResponse(code = 404, message = "Método de Pagamento não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<MetodoPagamento> metodoPagamento = service.getMetodoPagamentoById(id);
        if (!metodoPagamento.isPresent()) {
            return new ResponseEntity("Método de pagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(metodoPagamento.map(MetodoPagamentoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo método de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Método de Pagamento encontrado"),
            @ApiResponse(code = 404, message = "Método de Pagamento não encontrado")
    })
    public ResponseEntity post(@RequestBody MetodoPagamentoDTO dto) {
        try {
            MetodoPagamento metodoPagamento = converter(dto);
            metodoPagamento = service.salvar(metodoPagamento);
            return new ResponseEntity(metodoPagamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de um método de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Método de Pagamento excluído"),
            @ApiResponse(code = 404, message = "Método de Pagamento não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody MetodoPagamentoDTO dto) {
        if (!service.getMetodoPagamentoById(id).isPresent()) {
            return new ResponseEntity("Meétodo de pagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            MetodoPagamento metodoPagamento = converter(dto);
            metodoPagamento.setId(id);
            service.salvar(metodoPagamento);
            return ResponseEntity.ok(metodoPagamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um método de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Método de Pagamento encontrado"),
            @ApiResponse(code = 404, message = "Método de Pagamento não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<MetodoPagamento> metodoPagamento = service.getMetodoPagamentoById(id);
        if(!metodoPagamento.isPresent()) {
            return new ResponseEntity("Método de pagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(metodoPagamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MetodoPagamento converter(MetodoPagamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, MetodoPagamento.class);
    }
}
