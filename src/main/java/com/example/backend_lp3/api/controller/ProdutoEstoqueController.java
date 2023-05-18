package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ProdutoEstoqueDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.*;
import com.example.backend_lp3.model.entity.ProdutoEstoque;
import com.example.backend_lp3.service.*;
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
@RequestMapping("/api/v1/produtos-estoque")
@RequiredArgsConstructor
public class ProdutoEstoqueController {

    private final ProdutoEstoqueService service;
    private final ProdutoService produtoService;
    private final DepartamentoService departamentoService;
    private final CorService corService;
    private final TamanhoService tamanhoService;
    private final GeneroService generoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os produtos do estoque")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity get() {
        List<ProdutoEstoque> produtoEstoquesEstoque = service.getProdutosEstoque();
        return ResponseEntity.ok(produtoEstoquesEstoque.stream().map(ProdutoEstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto específico no estoque")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ProdutoEstoque> produtoEstoqueEstoque = service.getProdutoEstoqueById(id);
        if (!produtoEstoqueEstoque.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoEstoqueEstoque.map(ProdutoEstoqueDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo produto no estoque")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity post(@RequestBody ProdutoEstoqueDTO dto) {
        try {
            ProdutoEstoque produtoEstoque = converter(dto);
            Departamento departamento = departamentoService.salvar(produtoEstoque.getDepartamento());
            produtoEstoque.setDepartamento(departamento);
            Produto produto = produtoService.salvar(produtoEstoque.getProduto());
            produtoEstoque.setProduto(produto);
            Cor cor = corService.salvar(produtoEstoque.getCor());
            produtoEstoque.setCor(cor);
            Tamanho tamanho = tamanhoService.salvar(produtoEstoque.getTamanho());
            produtoEstoque.setTamanho(tamanho);
            Genero genero = generoService.salvar(produtoEstoque.getGenero());
            produtoEstoque.setGenero(genero);
            produtoEstoque = service.salvar(produtoEstoque);
            return new ResponseEntity(produtoEstoque, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de uma Produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody ProdutoEstoqueDTO dto) {
        if (!service.getProdutoEstoqueById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ProdutoEstoque produtoEstoque = converter(dto);
            produtoEstoque.setId(id);
            Departamento departamento = departamentoService.salvar(produtoEstoque.getDepartamento());
            produtoEstoque.setDepartamento(departamento);
            Cor cor = corService.salvar(produtoEstoque.getCor());
            produtoEstoque.setCor(cor);
            Tamanho tamanho = tamanhoService.salvar(produtoEstoque.getTamanho());
            produtoEstoque.setTamanho(tamanho);
            Genero genero = generoService.salvar(produtoEstoque.getGenero());
            produtoEstoque.setGenero(genero);
            service.salvar(produtoEstoque);
            return ResponseEntity.ok(produtoEstoque);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um Produto do estoque")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ProdutoEstoque> produtoEstoque = service.getProdutoEstoqueById(id);
        if(!produtoEstoque.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produtoEstoque.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ProdutoEstoque converter(ProdutoEstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoEstoque produtoEstoque = modelMapper.map(dto, ProdutoEstoque.class);
//        Departamento departamento = modelMapper.map(dto, Departamento.class);
//        produtoEstoque.setDepartamento(departamento);
//        Produto produto = modelMapper.map(dto, Produto.class);
//        produtoEstoque.setProduto(produto);
//        Cor cor = modelMapper.map(dto, Cor.class);
//        produtoEstoque.setCor(cor);
//        Tamanho tamanho = modelMapper.map(dto, Tamanho.class);
//        produtoEstoque.setTamanho(tamanho);
//        Genero genero = modelMapper.map(dto, Genero.class);
//        produtoEstoque.setGenero(genero);
        if (dto.getIdDepartamento() != null) {
            Optional<Departamento> departamento = departamentoService.getDepartamentoById(dto.getIdDepartamento());
            if (!departamento.isPresent()) {
                produtoEstoque.setDepartamento(null);
            } else {
                produtoEstoque.setDepartamento(departamento.get());
            }
        }
        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if (!produto.isPresent()) {
                produtoEstoque.setProduto(null);
            } else {
                produtoEstoque.setProduto(produto.get());
            }
        }
        if (dto.getIdCor() != null) {
            Optional<Cor> cor = corService.getCorById(dto.getIdCor());
            if (!cor.isPresent()) {
                produtoEstoque.setCor(null);
            } else {
                produtoEstoque.setCor(cor.get());
            }
        }
        if (dto.getIdTamanho() != null) {
            Optional<Tamanho> tamanho = tamanhoService.getTamanhoById(dto.getIdTamanho());
            if (!tamanho.isPresent()) {
                produtoEstoque.setTamanho(null);
            } else {
                produtoEstoque.setTamanho(tamanho.get());
            }
        }
        if (dto.getIdGenero() != null) {
            Optional<Genero> genero = generoService.getGeneroById(dto.getIdGenero());
            if (!genero.isPresent()) {
                produtoEstoque.setGenero(null);
            } else {
                produtoEstoque.setGenero(genero.get());
            }
        }

        return produtoEstoque;
    }
}
