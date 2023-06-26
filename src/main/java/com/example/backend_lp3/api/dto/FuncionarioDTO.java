package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        dto.logradouro = funcionario.getEndereco().getLogradouro();
        dto.numero = funcionario.getEndereco().getNumero();
        dto.complemento = funcionario.getEndereco().getComplemento();
        dto.bairro = funcionario.getEndereco().getBairro();
        dto.cidade = funcionario.getEndereco().getCidade();
        dto.uf = funcionario.getEndereco().getUf();
        dto.cep = funcionario.getEndereco().getCep();
        return dto;
    }
}
