package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
