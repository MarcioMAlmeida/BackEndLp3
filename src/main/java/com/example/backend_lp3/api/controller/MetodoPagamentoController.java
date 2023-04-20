package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.MetodoPagamentoDTO;
import com.example.backend_lp3.api.dto.MetodoPagamentoDTO;
import com.example.backend_lp3.api.dto.MetodoPagamentoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import com.example.backend_lp3.service.MetodoPagamentoService;
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
    public ResponseEntity get() {
        List<MetodoPagamento> metodosPagamento = service.getMetodosPagamento();
        return ResponseEntity.ok(metodosPagamento.stream().map(MetodoPagamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<MetodoPagamento> metodoPagamento = service.getMetodoPagamentoById(id);
        if (!metodoPagamento.isPresent()) {
            return new ResponseEntity("Metodo de pagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(metodoPagamento.map(MetodoPagamentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody MetodoPagamentoDTO dto) {
        try {
            MetodoPagamento metodoPagamento = converter(dto);
            metodoPagamento = service.salvar(metodoPagamento);
            return new ResponseEntity(metodoPagamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public MetodoPagamento converter(MetodoPagamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, MetodoPagamento.class);
    }
}
