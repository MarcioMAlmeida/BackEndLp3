package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Fornecedor;
import com.example.backend_lp3.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {

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
    private String cnpj;

    public static FornecedorDTO create(Fornecedor fornecedor) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(fornecedor, FornecedorDTO.class);
    }
}
