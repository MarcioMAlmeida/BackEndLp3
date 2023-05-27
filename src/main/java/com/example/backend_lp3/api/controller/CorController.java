package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.CorDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Cor;
import com.example.backend_lp3.service.CorService;
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
@RequestMapping("/api/v1/cores")
@RequiredArgsConstructor
@CrossOrigin
public class CorController {

    private final CorService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as Cores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cor encontrada"),
            @ApiResponse(code = 404, message = "Cor não encontrada")
    })
    public ResponseEntity get() {
        List<Cor> cores = service.getCores();
        return ResponseEntity.ok(cores.stream().map(CorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de Cor específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cor encontrada"),
            @ApiResponse(code = 404, message = "Cor não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cor> cor = service.getCorById(id);
        if (!cor.isPresent()) {

            return new ResponseEntity("Cor não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cor.map(CorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar nova cor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cor encontrada"),
            @ApiResponse(code = 404, message = "Cor não encontrada")
    })
    public ResponseEntity post(@RequestBody CorDTO dto) {
        try {
            Cor cor = converter(dto);
            cor = service.salvar(cor);
            return new ResponseEntity(cor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de uma Cor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cor encontrada"),
            @ApiResponse(code = 404, message = "Cor não encontrada")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody CorDTO dto) {
        if (!service.getCorById(id).isPresent()) {
            return new ResponseEntity("Cor não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Cor cor = converter(dto);
            cor.setId(id);
            service.salvar(cor);
            return ResponseEntity.ok(cor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar uma cor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cor excluída"),
            @ApiResponse(code = 404, message = "Cor não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cor> cor = service.getCorById(id);
        if(!cor.isPresent()) {
            return new ResponseEntity("Cor não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cor converter(CorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cor.class);
    }
}