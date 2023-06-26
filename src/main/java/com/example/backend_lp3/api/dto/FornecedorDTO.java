package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Fornecedor;
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
    private String cnpj;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static FornecedorDTO create(Fornecedor fornecedor) {
        ModelMapper modelMapper = new ModelMapper();
        FornecedorDTO dto = modelMapper.map(fornecedor, FornecedorDTO.class);
        dto.logradouro = fornecedor.getEndereco().getLogradouro();
        dto.numero = fornecedor.getEndereco().getNumero();
        dto.complemento = fornecedor.getEndereco().getComplemento();
        dto.bairro = fornecedor.getEndereco().getBairro();
        dto.cidade = fornecedor.getEndereco().getCidade();
        dto.uf = fornecedor.getEndereco().getUf();
        dto.cep = fornecedor.getEndereco().getCep();
        return dto;
    }
}
