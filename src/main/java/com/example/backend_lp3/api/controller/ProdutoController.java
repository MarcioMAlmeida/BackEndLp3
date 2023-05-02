package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ProdutoDTO;
import com.example.backend_lp3.api.dto.ProdutoDTO;
import com.example.backend_lp3.model.entity.Produto;
import com.example.backend_lp3.model.entity.Produto;
import com.example.backend_lp3.service.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os Produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity get() {
        List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de Produto específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }
}
