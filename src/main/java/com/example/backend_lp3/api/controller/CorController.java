package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.CorDTO;
import com.example.backend_lp3.api.dto.CorDTO;
import com.example.backend_lp3.model.entity.Cor;
import com.example.backend_lp3.model.entity.Cor;
import com.example.backend_lp3.service.CorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cores")
@RequiredArgsConstructor
public class CorController {

    private final CorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Cor> cores = service.getCores();
        return ResponseEntity.ok(cores.stream().map(CorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cor> cor = service.getCorById(id);
        if (!cor.isPresent()) {
            return new ResponseEntity("Cor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cor.map(CorDTO::create));
    }
}