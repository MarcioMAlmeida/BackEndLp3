package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.GerenteDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Endereco;
import com.example.backend_lp3.model.entity.Gerente;
import com.example.backend_lp3.service.EnderecoService;
import com.example.backend_lp3.service.GerenteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gerentes")
@RequiredArgsConstructor
public class GerenteController {

    private final GerenteService service;
    private final EnderecoService enderecoService;

    @GetMapping()

    public ResponseEntity get() {
        List<Gerente> gerentes = service.getGerentes();
        return ResponseEntity.ok(gerentes.stream().map(GerenteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Gerente> gerente = service.getGerenteById(id);
        if (!gerente.isPresent()) {
            return new ResponseEntity("Gerente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(gerente.map(GerenteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody GerenteDTO dto) {
        try {
            Gerente gerente = converter(dto);
            Endereco endereco = enderecoService.salvar(gerente.getEndereco());
            gerente.setEndereco(endereco);
            gerente = service.salvar(gerente);
            return new ResponseEntity(gerente, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, GerenteDTO dto) {
        if (!service.getGerenteById(id).isPresent()) {
            return new ResponseEntity("Gerente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Gerente gerente = converter(dto);
            gerente.setId(id);
            Endereco endereco = enderecoService.salvar(gerente.getEndereco());
            gerente.setEndereco(endereco);
            service.salvar(gerente);
            return ResponseEntity.ok(gerente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Gerente> gerente = service.getGerenteById(id);
        if(!gerente.isPresent()) {
            return new ResponseEntity("Gerente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(gerente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Gerente converter(GerenteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Gerente gerente = modelMapper.map(dto, Gerente.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        gerente.setEndereco(endereco);
        return gerente;
    }
}
