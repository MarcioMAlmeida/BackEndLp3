package com.example.backend_lp3.api.dto;

import com.example.backend_lp3.model.entity.Cliente;
import com.example.backend_lp3.model.entity.Funcionario;
import com.example.backend_lp3.model.entity.ProdutoEstoque;
import com.example.backend_lp3.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;
    private LocalDateTime dataVenda;
    private float precoTotal;
    private Funcionario funcionario;
    private Cliente cliente;
    private ProdutoEstoque produtoEstoque;

    public static VendaDTO create(Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(venda, VendaDTO.class);
    }
}
