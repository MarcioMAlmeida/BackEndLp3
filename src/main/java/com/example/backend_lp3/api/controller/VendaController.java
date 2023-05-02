package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.VendaDTO;
import com.example.backend_lp3.api.dto.VendaDTO;
import com.example.backend_lp3.model.entity.Venda;
import com.example.backend_lp3.model.entity.Venda;
import com.example.backend_lp3.service.VendaService;
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
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService service;

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
    @ApiOperation("Obter detalhes de Venda específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }
}
