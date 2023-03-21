package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

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

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
