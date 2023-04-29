package com.example.backend_lp3.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Pessoa {

    private String login;
    private String senha;
    private String cpf;
}
