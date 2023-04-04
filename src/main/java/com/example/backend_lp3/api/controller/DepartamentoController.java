package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.DepartamentoDTO;
import com.example.backend_lp3.model.entity.Departamento;
import com.example.backend_lp3.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Departamento> departamentos = service.getDepartamentos();
        return ResponseEntity.ok(departamentos.stream().map(DepartamentoDTO::create).collect(Collectors.toList()));
    }
}