package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.TamanhoDTO;
import com.example.backend_lp3.api.dto.TamanhoDTO;
import com.example.backend_lp3.model.entity.Tamanho;
import com.example.backend_lp3.model.entity.Tamanho;
import com.example.backend_lp3.service.TamanhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tamanhos")
@RequiredArgsConstructor
public class TamanhoController {

    private final TamanhoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Tamanho> tamanhos = service.getTamanhos();
        return ResponseEntity.ok(tamanhos.stream().map(TamanhoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Tamanho> tamanho = service.getTamanhoById(id);
        if (!tamanho.isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tamanho.map(TamanhoDTO::create));
    }
}
