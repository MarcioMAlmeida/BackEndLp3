package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Cor;
import com.example.backend_lp3.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorDTO {

    private Long id;
    private String nome;

    public static CorDTO create(Cor cor) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cor, CorDTO.class);
    }
}
