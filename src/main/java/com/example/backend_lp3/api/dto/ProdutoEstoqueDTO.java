package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstoqueDTO {

    private Long id;
    private int quantidade;
    private float precoUnitario;
    private Produto produto;
    private Departamento departamento;
    private Cor cor;
    private Tamanho tamanho;
    private Genero genero;

    public static ProdutoEstoqueDTO create(ProdutoEstoque produtoEstoque) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produtoEstoque, ProdutoEstoqueDTO.class);
    }
}
