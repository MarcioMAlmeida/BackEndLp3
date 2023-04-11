package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ProdutoEstoqueDTO;
import com.example.backend_lp3.api.dto.ProdutoEstoqueDTO;
import com.example.backend_lp3.model.entity.ProdutoEstoque;
import com.example.backend_lp3.model.entity.ProdutoEstoque;
import com.example.backend_lp3.service.ProdutoEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/produtos-estoque")
@RequiredArgsConstructor
public class ProdutoEstoqueController {

    private final ProdutoEstoqueService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ProdutoEstoque> produtosEstoque = service.getProdutosEstoque();
        return ResponseEntity.ok(produtosEstoque.stream().map(ProdutoEstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ProdutoEstoque> produtoEstoque = service.getProdutoEstoqueById(id);
        if (!produtoEstoque.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoEstoque.map(ProdutoEstoqueDTO::create));
    }
}
