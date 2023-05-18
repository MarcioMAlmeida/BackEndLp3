package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.ProdutoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedidoDTO {

    private Long id;

    private int quantidade;
    private Long idPedido;
    private Long idProdutoEstoque;

    public static ProdutoPedidoDTO create(ProdutoPedido produtoPedido) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produtoPedido, ProdutoPedidoDTO.class);
    }
}
