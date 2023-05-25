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
    private Integer quantidade;
    private Double precoUnitario;
    private Long idProduto;
    private Long idDepartamento;
    private Long idCor;
    private Long idTamanho;
    private Long idGenero;

    public static ProdutoEstoqueDTO create(ProdutoEstoque produtoEstoque) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produtoEstoque, ProdutoEstoqueDTO.class);
    }
}
