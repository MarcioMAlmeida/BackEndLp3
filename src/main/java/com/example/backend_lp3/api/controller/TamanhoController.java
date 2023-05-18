package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.TamanhoDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Tamanho;
import com.example.backend_lp3.service.TamanhoService;
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
@RequestMapping("/api/v1/tamanhos")
@RequiredArgsConstructor
public class TamanhoController {

    private final TamanhoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os Tamanhos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity get() {
        List<Tamanho> tamanhos = service.getTamanhos();
        return ResponseEntity.ok(tamanhos.stream().map(TamanhoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Tamanho específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Tamanho> tamanho = service.getTamanhoById(id);
        if (!tamanho.isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tamanho.map(TamanhoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo Tamanho")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity post(@RequestBody TamanhoDTO dto) {
        try {
            Tamanho tamanho = converter(dto);
            tamanho = service.salvar(tamanho);
            return new ResponseEntity(tamanho, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de um Tamanho")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody TamanhoDTO dto) {
        if (!service.getTamanhoById(id).isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Tamanho tamanho = converter(dto);
            tamanho.setId(id);
            service.salvar(tamanho);
            return ResponseEntity.ok(tamanho);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um Tamanho")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Tamanho> tamanho = service.getTamanhoById(id);
        if(!tamanho.isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tamanho.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Tamanho converter(TamanhoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Tamanho.class);
    }
}
