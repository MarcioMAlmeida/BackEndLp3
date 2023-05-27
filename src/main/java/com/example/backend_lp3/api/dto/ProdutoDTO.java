package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private Integer quantidade;
    private Integer quantidadeMin;
    private Integer quantidadeMax;
    private Double precoUnitario;
    private Long idDepartamento;
    private Long idCor;
    private Long idTamanho;
    private Long idGenero;

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }
}
