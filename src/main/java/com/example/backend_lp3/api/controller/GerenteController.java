package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.GerenteDTO;
import com.example.backend_lp3.model.entity.Gerente;
import com.example.backend_lp3.service.GerenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gerentes")
@RequiredArgsConstructor
public class GerenteController {

    private final GerenteService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Gerente> gerentes = service.getGerentes();
        return ResponseEntity.ok(gerentes.stream().map(GerenteDTO::create).collect(Collectors.toList()));
    }
}
