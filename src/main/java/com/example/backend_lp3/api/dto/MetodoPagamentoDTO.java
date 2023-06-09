package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Genero;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagamentoDTO {

    private Long id;
    private String nomeMetodoPagamento;

    public static MetodoPagamentoDTO create(MetodoPagamento metodoPagamento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(metodoPagamento, MetodoPagamentoDTO.class);
    }
}
