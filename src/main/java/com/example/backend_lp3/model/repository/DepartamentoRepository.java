package com.example.backend_lp3.model.repository;

import com.example.backend_lp3.model.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    boolean existsByNomeDepartamento(String nomeDepartamento);

}
