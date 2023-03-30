package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Gerente;
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
    private String cpf;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static GerenteDTO create(Gerente gerente) {
        ModelMapper modelMapper = new ModelMapper();
        GerenteDTO dto = modelMapper.map(gerente, GerenteDTO.class);
        dto.logradouro = gerente.getEndereco().getLogradouro();
        dto.numero = gerente.getEndereco().getNumero();
        dto.complemento = gerente.getEndereco().getComplemento();
        dto.bairro = gerente.getEndereco().getBairro();
        dto.cidade = gerente.getEndereco().getCidade();
        dto.uf = gerente.getEndereco().getUf();
        dto.cep = gerente.getEndereco().getCep();
        return dto;
    }
}
