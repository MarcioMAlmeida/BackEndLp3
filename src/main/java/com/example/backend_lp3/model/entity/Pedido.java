package com.example.backend_lp3.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private Double precoTotal;

    @ManyToOne
    private Fornecedor fornecedor;
    @ManyToOne
    private Gerente gerente;

}
