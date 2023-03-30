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
    private String cpf;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static ClienteDTO create(Cliente aluno) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(aluno, ClienteDTO.class);
        dto.logradouro = aluno.getEndereco().getLogradouro();
        dto.numero = aluno.getEndereco().getNumero();
        dto.complemento = aluno.getEndereco().getComplemento();
        dto.bairro = aluno.getEndereco().getBairro();
        dto.cidade = aluno.getEndereco().getCidade();
        dto.uf = aluno.getEndereco().getUf();
        dto.cep = aluno.getEndereco().getCep();
        return dto;
    }
}
