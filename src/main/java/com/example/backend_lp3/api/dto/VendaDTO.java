package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;
    private LocalDateTime dataVenda;
    private float precoTotal;
    private Long idFuncionario;
    private Long idCliente;
    private Long idProdutoEstoque;
    private Long idMetodoPagamento;

    public static VendaDTO create(Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(venda, VendaDTO.class);
    }
}
