package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.*;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private float precoTotal;
    private Fornecedor fornecedor;
    private Gerente gerente;
    private ProdutoEstoque produtoEstoque;

    public static PedidoDTO create(Pedido pedido) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pedido, PedidoDTO.class);
    }
}
