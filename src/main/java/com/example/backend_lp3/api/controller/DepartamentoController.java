package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.DepartamentoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Departamento;
import com.example.backend_lp3.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Departamento> departamentos = service.getDepartamentos();
        return ResponseEntity.ok(departamentos.stream().map(DepartamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Departamento> departamento = service.getDepartamentoById(id);
        if (!departamento.isPresent()) {
            return new ResponseEntity("Departamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(departamento.map(DepartamentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody DepartamentoDTO dto) {
        try {
            Departamento departamento = converter(dto);
            departamento = service.salvar(departamento);
            return new ResponseEntity(departamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, DepartamentoDTO dto) {
        if (!service.getDepartamentoById(id).isPresent()) {
            return new ResponseEntity("Departamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Departamento departamento = converter(dto);
            departamento.setId(id);
            service.salvar(departamento);
            return ResponseEntity.ok(departamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Departamento> departamento = service.getDepartamentoById(id);
        if(!departamento.isPresent()) {
            return new ResponseEntity("Departamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(departamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Departamento converter(DepartamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Departamento.class);
    }
}
