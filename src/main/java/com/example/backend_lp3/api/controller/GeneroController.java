package com.example.backend_lp3.api.controller;

import com.example.backend_lp3.api.dto.GeneroDTO;
import com.example.backend_lp3.exception.RegraNegocioException;
import com.example.backend_lp3.model.entity.Genero;
import com.example.backend_lp3.service.GeneroService;
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
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
@CrossOrigin
public class GeneroController {

    private final GeneroService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os gêneros")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero encontrado"),
            @ApiResponse(code = 404, message = "Gênero não encontrado")
    })
    public ResponseEntity get() {
        List<Genero> generos = service.getGeneros();
        return ResponseEntity.ok(generos.stream().map(GeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Gênero específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero encontrada"),
            @ApiResponse(code = 404, message = "Gênero não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.getGeneroById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(genero.map(GeneroDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar novo Gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero encontrada"),
            @ApiResponse(code = 404, message = "Gênero não encontrada")
    })
    public ResponseEntity post(@RequestBody GeneroDTO dto) {
        try {
            Genero genero = converter(dto);
            genero = service.salvar(genero);
            return new ResponseEntity(genero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar dados de um Gênero")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gênero encontrado"),
            @ApiResponse(code = 404, message = "Gênero não encontrado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody GeneroDTO dto) {
        if (!service.getGeneroById(id).isPresent()) {
            return new ResponseEntity("Genero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Genero genero = converter(dto);
            genero.setId(id);
            service.salvar(genero);
            return ResponseEntity.ok(genero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar um Gênero")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Gênero excluído"),
            @ApiResponse(code = 404, message = "Gênero não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.getGeneroById(id);
        if(!genero.isPresent()) {
            return new ResponseEntity("Genero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(genero.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Genero converter(GeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Genero.class);
    }

}