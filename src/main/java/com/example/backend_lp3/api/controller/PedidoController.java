package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.PedidoDTO;
import com.example.backend_lp3.api.dto.PedidoDTO;
import com.example.backend_lp3.model.entity.Pedido;
import com.example.backend_lp3.model.entity.Pedido;
import com.example.backend_lp3.service.PedidoService;
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
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

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
}
