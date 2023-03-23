package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Genero;
import com.example.backend_lp3.model.entity.Gerente;
import com.example.backend_lp3.model.entity.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerenteDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cep;
    private String estado;
    private String cidade;
    private String logradouro;
    private String numero;
    private String complemento;
    private String login;
    private String senha;
    private String cpf;

    public static GerenteDTO create(Gerente gerente) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(gerente, GerenteDTO.class);
    }
}
