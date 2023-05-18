package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.VendaDTO;
import com.example.backend_lp3.api.dto.VendaDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.*;
import com.example.backend_lp3.model.entity.Venda;
import com.example.backend_lp3.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService service;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final MetodoPagamentoService metodoPagamentoService;
    private final ProdutoEstoqueService produtoEstoqueService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as Vendas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Venda específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova Venda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity post(@RequestBody VendaDTO dto) {
        try {
            Venda venda = converter(dto);
            Funcionario funcionario = funcionarioService.salvar(venda.getFuncionario());
            venda.setFuncionario(funcionario);
            Cliente cliente = clienteService.salvar(venda.getCliente());
            venda.setCliente(cliente);
            MetodoPagamento metodoPagamento = metodoPagamentoService.salvar(venda.getMetodoPagamento());
            venda.setMetodoPagamento(metodoPagamento);
            ProdutoEstoque produtoEstoque = produtoEstoqueService.salvar(venda.getProdutoEstoque());
            venda.setProdutoEstoque(produtoEstoque);
            venda.setDataVenda(LocalDateTime.now(ZoneId.of("UTC")));
            venda = service.salvar(venda);
            return new ResponseEntity(venda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de uma Venda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, VendaDTO dto) {
        if (!service.getVendaById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Venda venda = converter(dto);
            venda.setId(id);
            Funcionario funcionario = funcionarioService.salvar(venda.getFuncionario());
            venda.setFuncionario(funcionario);
            Cliente cliente = clienteService.salvar(venda.getCliente());
            venda.setCliente(cliente);
            ProdutoEstoque produtoEstoque = produtoEstoqueService.salvar(venda.getProdutoEstoque());
            venda.setProdutoEstoque(produtoEstoque);
            MetodoPagamento metodoPagamento = metodoPagamentoService.salvar(venda.getMetodoPagamento());
            venda.setMetodoPagamento(metodoPagamento);
            service.salvar(venda);
            return ResponseEntity.ok(venda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar uma Venda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if(!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(venda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                venda.setFuncionario(null);
            } else {
                venda.setFuncionario(funcionario.get());
            }
        }
        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if (!cliente.isPresent()) {
                venda.setCliente(null);
            } else {
                venda.setCliente(cliente.get());
            }
        }
        if (dto.getIdMetodoPagamento() != null) {
            Optional<MetodoPagamento> metodoPagamento = metodoPagamentoService.getMetodoPagamentoById(dto.getIdMetodoPagamento());
            if (!metodoPagamento.isPresent()) {
                venda.setMetodoPagamento(null);
            } else {
                venda.setMetodoPagamento(metodoPagamento.get());
            }
        }
        if (dto.getIdProdutoEstoque() != null) {
            Optional<ProdutoEstoque> produtoEstoque = produtoEstoqueService.getProdutoEstoqueById(dto.getIdProdutoEstoque());
            if (!produtoEstoque.isPresent()) {
                venda.setProdutoEstoque(null);
            } else {
                venda.setProdutoEstoque(produtoEstoque.get());
            }
        }
        return venda;
    }
}
