package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoDTO {

    private Long id;
    private String nome;

    public static MetodoPagamentoDTO create(MetodoPagamento metodoPagamento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(metodoPagamento, MetodoPagamentoDTO.class);
    }
}
