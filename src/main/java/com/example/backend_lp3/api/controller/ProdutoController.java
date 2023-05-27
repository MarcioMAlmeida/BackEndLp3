package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ProdutoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.*;
import com.example.backend_lp3.model.entity.Produto;
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
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@CrossOrigin
public class ProdutoController {

    private final ProdutoService service;
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
        List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Produto> produtoEstoque = service.getProdutoById(id);
        if (!produtoEstoque.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoEstoque.map(ProdutoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity post(@RequestBody ProdutoDTO dto) {
        try {
            Produto produto = converter(dto);
            Departamento departamento = departamentoService.salvar(produto.getDepartamento());
            produto.setDepartamento(departamento);
            Cor cor = corService.salvar(produto.getCor());
            produto.setCor(cor);
            Tamanho tamanho = tamanhoService.salvar(produto.getTamanho());
            produto.setTamanho(tamanho);
            Genero genero = generoService.salvar(produto.getGenero());
            produto.setGenero(genero);
            produto = service.salvar(produto);
            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de um Produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody ProdutoDTO dto) {
        if (!service.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produto produto = converter(dto);
            produto.setId(id);
            Departamento departamento = departamentoService.salvar(produto.getDepartamento());
            produto.setDepartamento(departamento);
            Cor cor = corService.salvar(produto.getCor());
            produto.setCor(cor);
            Tamanho tamanho = tamanhoService.salvar(produto.getTamanho());
            produto.setTamanho(tamanho);
            Genero genero = generoService.salvar(produto.getGenero());
            produto.setGenero(genero);
            service.salvar(produto);
            return ResponseEntity.ok(produto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um Produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto excluído"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if(!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        if (dto.getIdDepartamento() != null) {
            Optional<Departamento> departamento = departamentoService.getDepartamentoById(dto.getIdDepartamento());
            if (!departamento.isPresent()) {
                produto.setDepartamento(null);
            } else {
                produto.setDepartamento(departamento.get());
            }
        }
        if (dto.getIdCor() != null) {
            Optional<Cor> cor = corService.getCorById(dto.getIdCor());
            if (!cor.isPresent()) {
                produto.setCor(null);
            } else {
                produto.setCor(cor.get());
            }
        }
        if (dto.getIdTamanho() != null) {
            Optional<Tamanho> tamanho = tamanhoService.getTamanhoById(dto.getIdTamanho());
            if (!tamanho.isPresent()) {
                produto.setTamanho(null);
            } else {
                produto.setTamanho(tamanho.get());
            }
        }
        if (dto.getIdGenero() != null) {
            Optional<Genero> genero = generoService.getGeneroById(dto.getIdGenero());
            if (!genero.isPresent()) {
                produto.setGenero(null);
            } else {
                produto.setGenero(genero.get());
            }
        }
        return produto;
    }
}