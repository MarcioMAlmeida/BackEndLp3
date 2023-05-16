package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.PedidoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.*;
import com.example.backend_lp3.model.entity.Pedido;
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
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final GerenteService gerenteService;
    private final FornecedorService fornecedorService;
    private final ProdutoEstoqueService produtoEstoqueService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os Pedidos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado")
    })
    public ResponseEntity get() {
        List<Pedido> pedidos = service.getPedidos();
        return ResponseEntity.ok(pedidos.stream().map(PedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de Pedido específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrado"),
            @ApiResponse(code = 404, message = "Pedido não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pedido.map(PedidoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova Pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrada"),
            @ApiResponse(code = 404, message = "Pedido não encontrada")
    })
    public ResponseEntity post(@RequestBody PedidoDTO dto) {
        try {
            Pedido pedido = converter(dto);
            Gerente gerente = gerenteService.salvar(pedido.getGerente());
            pedido.setGerente(gerente);
            Fornecedor fornecedor = fornecedorService.salvar(pedido.getFornecedor());
            pedido.setFornecedor(fornecedor);
            ProdutoEstoque produtoEstoque = produtoEstoqueService.salvar(pedido.getProdutoEstoque());
            pedido.setProdutoEstoque(produtoEstoque);
            pedido = service.salvar(pedido);
            return new ResponseEntity(pedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de uma Pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrada"),
            @ApiResponse(code = 404, message = "Pedido não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, PedidoDTO dto) {
        if (!service.getPedidoById(id).isPresent()) {
            return new ResponseEntity("Pedido não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Pedido pedido = converter(dto);
            pedido.setId(id);
            Gerente gerente = gerenteService.salvar(pedido.getGerente());
            pedido.setGerente(gerente);
            Fornecedor fornecedor = fornecedorService.salvar(pedido.getFornecedor());
            pedido.setFornecedor(fornecedor);
            ProdutoEstoque produtoEstoque = produtoEstoqueService.salvar(pedido.getProdutoEstoque());
            pedido.setProdutoEstoque(produtoEstoque);
            service.salvar(pedido);
            return ResponseEntity.ok(pedido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar uma Pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrada"),
            @ApiResponse(code = 404, message = "Pedido não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if(!pedido.isPresent()) {
            return new ResponseEntity("Pedido não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pedido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Pedido converter(PedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        if (dto.getIdGerente() != null) {
            Optional<Gerente> gerente = gerenteService.getGerenteById(dto.getIdGerente());
            if (!gerente.isPresent()) {
                pedido.setGerente(null);
            } else {
                pedido.setGerente(gerente.get());
            }
        }
        if (dto.getIdFornecedor() != null) {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(dto.getIdFornecedor());
            if (!fornecedor.isPresent()) {
                pedido.setFornecedor(null);
            } else {
                pedido.setFornecedor(fornecedor.get());
            }
        }
        if (dto.getIdProdutoEstoque() != null) {
            Optional<ProdutoEstoque> produtoEstoque = produtoEstoqueService.getProdutoEstoqueById(dto.getIdProdutoEstoque());
            if (!produtoEstoque.isPresent()) {
                pedido.setProdutoEstoque(null);
            } else {
                pedido.setProdutoEstoque(produtoEstoque.get());
            }
        }
        return pedido;
    }
}
