package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.FornecedorDTO;
import com.example.backend_lp3.api.dto.FornecedorDTO;
import com.example.backend_lp3.model.entity.Fornecedor;
import com.example.backend_lp3.model.entity.Fornecedor;
import com.example.backend_lp3.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }
}