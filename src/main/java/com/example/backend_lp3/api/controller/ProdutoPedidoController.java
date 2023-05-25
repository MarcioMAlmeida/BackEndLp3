package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ProdutoPedidoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Pedido;
import com.example.backend_lp3.model.entity.Gerente;
import com.example.backend_lp3.model.entity.ProdutoPedido;
import com.example.backend_lp3.model.entity.ProdutoEstoque;
import com.example.backend_lp3.service.PedidoService;
import com.example.backend_lp3.service.GerenteService;
import com.example.backend_lp3.service.ProdutoPedidoService;
import com.example.backend_lp3.service.ProdutoEstoqueService;
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
@RequestMapping("/api/v1/produtos-pedido")
@RequiredArgsConstructor
@CrossOrigin
public class ProdutoPedidoController {

    private final ProdutoPedidoService service;
    private final PedidoService pedidoService;
    private final ProdutoEstoqueService produtoEstoqueService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os ProdutoPedidos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoPedido encontrado"),
            @ApiResponse(code = 404, message = "ProdutoPedido não encontrado")
    })
    public ResponseEntity get() {
        List<ProdutoPedido> produtoPedidos = service.getProdutoPedidos();
        return ResponseEntity.ok(produtoPedidos.stream().map(ProdutoPedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de ProdutoPedido específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoPedido encontrado"),
            @ApiResponse(code = 404, message = "ProdutoPedido não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ProdutoPedido> produtoPedido = service.getProdutoPedidoById(id);
        if (!produtoPedido.isPresent()) {
            return new ResponseEntity("ProdutoPedido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoPedido.map(ProdutoPedidoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova ProdutoPedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoPedido encontrada"),
            @ApiResponse(code = 404, message = "ProdutoPedido não encontrada")
    })
    public ResponseEntity post(@RequestBody ProdutoPedidoDTO dto) {
        try {
            ProdutoPedido produtoPedido = converter(dto);
            Pedido pedido = pedidoService.salvar(produtoPedido.getPedido());
            produtoPedido.setPedido(pedido);
            ProdutoEstoque produtoEstoque = produtoEstoqueService.salvar(produtoPedido.getProdutoEstoque());
            produtoPedido.setProdutoEstoque(produtoEstoque);
            produtoPedido = service.salvar(produtoPedido);
            return new ResponseEntity(produtoPedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de uma ProdutoPedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoPedido encontrada"),
            @ApiResponse(code = 404, message = "ProdutoPedido não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody ProdutoPedidoDTO dto) {
        if (!service.getProdutoPedidoById(id).isPresent()) {
            return new ResponseEntity("ProdutoPedido não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ProdutoPedido produtoPedido = converter(dto);
            produtoPedido.setId(id);
            Pedido pedido = pedidoService.salvar(produtoPedido.getPedido());
            produtoPedido.setPedido(pedido);
            ProdutoEstoque produtoEstoque = produtoEstoqueService.salvar(produtoPedido.getProdutoEstoque());
            produtoPedido.setProdutoEstoque(produtoEstoque);
            service.salvar(produtoPedido);
            return ResponseEntity.ok(produtoPedido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar uma ProdutoPedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "ProdutoPedido excluído"),
            @ApiResponse(code = 404, message = "ProdutoPedido não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ProdutoPedido> produtoPedido = service.getProdutoPedidoById(id);
        if(!produtoPedido.isPresent()) {
            return new ResponseEntity("ProdutoPedido não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produtoPedido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ProdutoPedido converter(ProdutoPedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoPedido produtoPedido = modelMapper.map(dto, ProdutoPedido.class);
        if (dto.getIdPedido() != null) {
            Optional<Pedido> pedido = pedidoService.getPedidoById(dto.getIdPedido());
            if (!pedido.isPresent()) {
                produtoPedido.setPedido(null);
            } else {
                produtoPedido.setPedido(pedido.get());
            }
        }
        if (dto.getIdProdutoEstoque() != null) {
            Optional<ProdutoEstoque> produtoEstoque = produtoEstoqueService.getProdutoEstoqueById(dto.getIdProdutoEstoque());
            if (!produtoEstoque.isPresent()) {
                produtoPedido.setProdutoEstoque(null);
            } else {
                produtoPedido.setProdutoEstoque(produtoEstoque.get());
            }
        }
        return produtoPedido;
    }
}
