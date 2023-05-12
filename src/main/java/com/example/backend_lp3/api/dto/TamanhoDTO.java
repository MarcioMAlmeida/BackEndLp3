package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Tamanho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TamanhoDTO {

    private Long id;
    private String nomeTamanho;

    public static TamanhoDTO create(Tamanho tamanho) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tamanho, TamanhoDTO.class);
    }
}
