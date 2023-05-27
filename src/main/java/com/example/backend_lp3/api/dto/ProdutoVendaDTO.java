package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.ProdutoVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendaDTO {

    private Long id;

    private Integer quantidade;
    private Long idVenda;
    private Long idProduto;

    public static ProdutoVendaDTO create(ProdutoVenda produtoVenda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produtoVenda, ProdutoVendaDTO.class);
    }
}
