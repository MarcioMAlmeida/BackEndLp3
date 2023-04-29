package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.ClienteDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Cliente;
import com.example.backend_lp3.model.entity.Endereco;
import com.example.backend_lp3.service.ClienteService;
import com.example.backend_lp3.service.EnderecoService;
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
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os Clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (cliente.isEmpty()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = converter(dto);
            Endereco endereco = enderecoService.salvar(cliente.getEndereco());
            cliente.setEndereco(endereco);
            cliente = service.salvar(cliente);
            return new ResponseEntity(cliente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ClienteDTO dto) {
        if (!service.getClienteById(id).isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cliente cliente = converter(dto);
            cliente.setId(id);
            Endereco endereco = enderecoService.salvar(cliente.getEndereco());
            cliente.setEndereco(endereco);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if(!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cliente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        cliente.setEndereco(endereco);
        return cliente;
    }
}
