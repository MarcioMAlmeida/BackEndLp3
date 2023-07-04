package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ProdutoVendaDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Produto;
import com.example.backend_lp3.model.entity.Venda;
import com.example.backend_lp3.model.entity.ProdutoVenda;
import com.example.backend_lp3.service.ProdutoService;
import com.example.backend_lp3.service.VendaService;
import com.example.backend_lp3.service.ProdutoVendaService;
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
@RequestMapping("/api/v1/produtos-venda")
@RequiredArgsConstructor
@CrossOrigin
public class ProdutoVendaController {

    private final ProdutoVendaService service;
    private final VendaService vendaService;
    private final ProdutoService produtoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os ProdutoVendas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoVenda encontrado"),
            @ApiResponse(code = 404, message = "ProdutoVenda não encontrado")
    })
    public ResponseEntity get() {
        List<ProdutoVenda> produtoVendas = service.getProdutoVendas();
        return ResponseEntity.ok(produtoVendas.stream().map(ProdutoVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de ProdutoVenda específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoVenda encontrado"),
            @ApiResponse(code = 404, message = "ProdutoVenda não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ProdutoVenda> produtoVenda = service.getProdutoVendaById(id);
        if (!produtoVenda.isPresent()) {
            return new ResponseEntity("ProdutoVenda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtoVenda.map(ProdutoVendaDTO::create));
    }

    @GetMapping("/vendas/{idVenda}")
    public ResponseEntity<?> getProdutosVendaByVendaId(@PathVariable("idVenda") Long idVenda) {
        try {
            return ResponseEntity.ok(service.getProdutosVendaByVendaId(idVenda));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao carregar Produtos do Venda");
        }
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova ProdutoVenda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoVenda encontrada"),
            @ApiResponse(code = 404, message = "ProdutoVenda não encontrada")
    })
    public ResponseEntity post(@RequestBody ProdutoVendaDTO dto) {
        try {
            ProdutoVenda produtoVenda = converter(dto);
            Venda venda = vendaService.salvar(produtoVenda.getVenda());
            produtoVenda.setVenda(venda);
            Produto produto = produtoService.salvar(produtoVenda.getProduto());
            produtoVenda.setProduto(produto);
            produtoVenda = service.salvar(produtoVenda);
            return new ResponseEntity(produtoVenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de uma ProdutoVenda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ProdutoVenda encontrada"),
            @ApiResponse(code = 404, message = "ProdutoVenda não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody ProdutoVendaDTO dto) {
        if (!service.getProdutoVendaById(id).isPresent()) {
            return new ResponseEntity("ProdutoVenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ProdutoVenda produtoVenda = converter(dto);
            produtoVenda.setId(id);
            Venda venda = vendaService.salvar(produtoVenda.getVenda());
            produtoVenda.setVenda(venda);
            Produto produto = produtoService.salvar(produtoVenda.getProduto());
            produtoVenda.setProduto(produto);
            service.salvar(produtoVenda);
            return ResponseEntity.ok(produtoVenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar uma ProdutoVenda")
    @ApiResponses({
            @ApiResponse(code = 204, message = "ProdutoVenda excluído"),
            @ApiResponse(code = 404, message = "ProdutoVenda não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ProdutoVenda> produtoVenda = service.getProdutoVendaById(id);
        if(!produtoVenda.isPresent()) {
            return new ResponseEntity("ProdutoVenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produtoVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ProdutoVenda converter(ProdutoVendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoVenda produtoVenda = modelMapper.map(dto, ProdutoVenda.class);
        if (dto.getIdVenda() != null) {
            Optional<Venda> venda = vendaService.getVendaById(dto.getIdVenda());
            if (!venda.isPresent()) {
                produtoVenda.setVenda(null);
            } else {
                produtoVenda.setVenda(venda.get());
            }
        }
        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if (!produto.isPresent()) {
                produtoVenda.setProduto(null);
            } else {
                produtoVenda.setProduto(produto.get());
            }
        }
        return produtoVenda;
    }
}
